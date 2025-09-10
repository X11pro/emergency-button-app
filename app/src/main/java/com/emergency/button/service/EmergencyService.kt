package com.emergency.button.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.emergency.button.utils.EmergencyManager
import com.emergency.button.utils.EmergencyPreferences

class EmergencyService : Service() {
    
    private val TAG = "EmergencyService"
    private var emergencyManager: EmergencyManager? = null
    private var emergencyPreferences: EmergencyPreferences? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "EmergencyService created")
        
        emergencyManager = EmergencyManager(this)
        emergencyManager?.initialize()
        
        emergencyPreferences = EmergencyPreferences(this)
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "EmergencyService started with action: ${intent?.action}")
        Log.d(TAG, "Intent extras: ${intent?.extras}")
        
        when (intent?.action) {
            "EMERGENCY_WIDGET_TRIGGER" -> {
                val source = intent.getStringExtra("source") ?: "unknown"
                Log.d(TAG, "Emergency triggered from: $source")
                executeEmergencyDirectly()
            }
            else -> {
                Log.w(TAG, "Unknown action: ${intent?.action}")
            }
        }
        
        // Stop service after handling
        stopSelf()
        return START_NOT_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
    private fun executeEmergencyDirectly() {
        if (emergencyManager?.hasEmergencyContacts() == true) {
            Log.d(TAG, "Executing emergency directly from service")
            
            // Get emergency options from preferences
            val sendSMS = emergencyPreferences?.getEmergencySendSMS() ?: true
            val makeCall = emergencyPreferences?.getEmergencyMakeCall() ?: true
            val optionName = emergencyPreferences?.getEmergencyOptionName() ?: "SMS + Call"
            
            Log.d(TAG, "Emergency options - SMS: $sendSMS, Call: $makeCall, Option: $optionName")
            
            emergencyManager?.executeEmergencyWithOptions({ success ->
                if (success) {
                    Log.d(TAG, "Emergency executed successfully from service")
                    // Show toast on main thread with the actual action taken
                    android.os.Handler(android.os.Looper.getMainLooper()).post {
                        val successText = when {
                            sendSMS && makeCall -> "Emergency SMS and call sent!"
                            sendSMS -> "Emergency SMS sent!"
                            makeCall -> "Emergency call made!"
                            else -> "Emergency sent!"
                        }
                        Toast.makeText(this@EmergencyService, successText, Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e(TAG, "Emergency execution failed from service")
                    // Show toast on main thread
                    android.os.Handler(android.os.Looper.getMainLooper()).post {
                        Toast.makeText(this@EmergencyService, "Failed to send emergency", Toast.LENGTH_LONG).show()
                    }
                }
            }, sendSMS, makeCall)
        } else {
            Log.w(TAG, "No emergency contacts available")
            // Show toast on main thread
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                Toast.makeText(this@EmergencyService, "No emergency contacts configured", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "EmergencyService destroyed")
    }
}
