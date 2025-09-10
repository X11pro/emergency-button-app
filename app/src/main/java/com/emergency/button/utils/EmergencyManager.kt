package com.emergency.button.utils

import android.content.Context
import android.content.Intent
import android.hardware.camera2.CameraManager
import android.location.Location
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.VibrationEffect
import android.os.Vibrator
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import kotlinx.coroutines.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class EmergencyManager(private val context: Context) {
    
    private val TAG = "EmergencyManager"
    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private val emergencyPreferences = EmergencyPreferences(context)
    private val contactManager = ContactManager(context)
    
    private var isInitialized = false
    private var currentLocation: Location? = null
    
    fun initialize() {
        if (!isInitialized) {
            Log.d(TAG, "Initializing EmergencyManager")
            isInitialized = true
        }
    }
    
    fun hasEmergencyContacts(): Boolean {
        return contactManager.getEmergencyContacts().isNotEmpty()
    }
    
    fun executeEmergency(callback: (Boolean) -> Unit) {
        executeEmergencyWithOptions(callback, sendSMS = true, makeCall = true)
    }
    
    fun executeEmergencyWithOptions(callback: (Boolean) -> Unit, sendSMS: Boolean = true, makeCall: Boolean = true) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Executing emergency sequence - SMS: $sendSMS, Call: $makeCall")
                
                // Get location
                val location = getCurrentLocation()
                
                // Get emergency message
                val message = emergencyPreferences.getEmergencyMessage()
                
                var smsSuccess = false
                var callSuccess = false
                
                // Send SMS to all contacts if requested
                if (sendSMS) {
                    smsSuccess = sendEmergencySMS(location, message)
                }
                
                // Make emergency call to first contact if requested
                if (makeCall) {
                    callSuccess = makeEmergencyCall()
                }
                
                // Activate physical alerts
                activatePhysicalAlerts()
                
                // Return success if at least one action worked
                val success = smsSuccess || callSuccess || (!sendSMS && !makeCall)
                callback(success)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error executing emergency", e)
                callback(false)
            }
        }
    }
    
    private suspend fun getCurrentLocation(): Location? {
        return try {
            Log.d(TAG, "Getting current location")
            
            val location = withContext(Dispatchers.IO) {
                val task = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    object : CancellationToken() {
                        override fun onCanceledRequested(listener: OnTokenCanceledListener): CancellationToken {
                            return CancellationTokenSource().token
                        }
                        
                        override fun isCancellationRequested(): Boolean {
                            return false
                        }
                    }
                )
                
                // Convert Task to suspend function
                suspendCoroutine<Location?> { continuation ->
                    task.addOnSuccessListener { location ->
                        continuation.resume(location)
                    }.addOnFailureListener { exception ->
                        Log.e(TAG, "Failed to get location", exception)
                        continuation.resume(null)
                    }
                }
            }
            
            Log.d(TAG, "Location obtained: ${location?.latitude}, ${location?.longitude}")
            location
            
        } catch (e: Exception) {
            Log.e(TAG, "Error getting location", e)
            null
        }
    }
    
    private fun sendEmergencySMS(location: Location?, message: String): Boolean {
        return try {
            Log.d(TAG, "Sending emergency SMS")
            
            val contacts = contactManager.getEmergencyContacts()
            if (contacts.isEmpty()) {
                Log.w(TAG, "No emergency contacts configured")
                return false
            }
            
            val smsManager = SmsManager.getDefault()
            val locationText = if (location != null) {
                "https://maps.google.com/?q=${location.latitude},${location.longitude}"
            } else {
                "Location unavailable"
            }
            
            // Ensure the message includes location information
            val fullMessage = if (message.contains("location", ignoreCase = true)) {
                "$message $locationText"
            } else {
                "$message\n\nLocation: $locationText"
            }
            
            var successCount = 0
            for (contact in contacts) {
                try {
                    smsManager.sendTextMessage(
                        contact.phoneNumber,
                        null,
                        fullMessage,
                        null,
                        null
                    )
                    successCount++
                    Log.d(TAG, "SMS sent to ${contact.name}")
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to send SMS to ${contact.name}", e)
                }
            }
            
            Log.d(TAG, "SMS sent to $successCount/${contacts.size} contacts")
            successCount > 0
            
        } catch (e: Exception) {
            Log.e(TAG, "Error sending SMS", e)
            false
        }
    }
    
    private fun makeEmergencyCall(): Boolean {
        return try {
            Log.d(TAG, "Making emergency call")
            
            val contacts = contactManager.getEmergencyContacts()
            if (contacts.isNotEmpty()) {
                val firstContact = contacts.first()
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = android.net.Uri.parse("tel:${firstContact.phoneNumber}")
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
                
                Log.d(TAG, "Emergency call initiated to ${firstContact.name}")
                true
            } else {
                Log.w(TAG, "No contacts available for emergency call")
                false
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error making emergency call", e)
            false
        }
    }
    
    private fun activatePhysicalAlerts() {
        try {
            Log.d(TAG, "Activating physical alerts")
            
            // Vibrate
            activateVibration()
            
            // Flashlight
            activateFlashlight()
            
            // Sound alert
            activateSoundAlert()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error activating physical alerts", e)
        }
    }
    
    private fun activateVibration() {
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (vibrator.hasVibrator()) {
                val pattern = longArrayOf(0, 500, 200, 500, 200, 500, 200, 500)
                val amplitudes = intArrayOf(0, 255, 0, 255, 0, 255, 0, 255)
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, amplitudes, -1))
                Log.d(TAG, "Vibration activated")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error activating vibration", e)
        }
    }
    
    private fun activateFlashlight() {
        try {
            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                val characteristics = cameraManager.getCameraCharacteristics(id)
                val flashAvailable = characteristics.get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE)
                flashAvailable == true
            }
            
            if (cameraId != null) {
                cameraManager.setTorchMode(cameraId, true)
                
                // Turn off after 10 seconds
                CoroutineScope(Dispatchers.IO).launch {
                    delay(10000)
                    try {
                        cameraManager.setTorchMode(cameraId, false)
                    } catch (e: Exception) {
                        Log.e(TAG, "Error turning off flashlight", e)
                    }
                }
                
                Log.d(TAG, "Flashlight activated")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error activating flashlight", e)
        }
    }
    
    private fun activateSoundAlert() {
        try {
            val toneGenerator = ToneGenerator(AudioManager.STREAM_ALARM, 100)
            toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 5000)
            
            CoroutineScope(Dispatchers.IO).launch {
                delay(5000)
                toneGenerator.release()
            }
            
            Log.d(TAG, "Sound alert activated")
        } catch (e: Exception) {
            Log.e(TAG, "Error activating sound alert", e)
        }
    }
    
    fun testEmergency(callback: (Boolean) -> Unit) {
        Log.d(TAG, "Testing emergency system")
        executeEmergency(callback)
    }
    
    fun testLocation(callback: (String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Testing location functionality")
                val location = getCurrentLocation()
                
                val locationText = if (location != null) {
                    val googleMapsUrl = "https://maps.google.com/?q=${location.latitude},${location.longitude}"
                    "Location found: ${location.latitude}, ${location.longitude}\nGoogle Maps: $googleMapsUrl"
                } else {
                    "Location unavailable - check permissions and GPS"
                }
                
                Log.d(TAG, "Location test result: $locationText")
                callback(locationText)
                
            } catch (e: Exception) {
                Log.e(TAG, "Error testing location", e)
                callback("Error getting location: ${e.message}")
            }
        }
    }
}
