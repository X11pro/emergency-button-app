

package com.emergency.sos.button

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.emergency.sos.button.databinding.ActivityMainBinding
import com.emergency.sos.button.utils.EmergencyManager
import com.emergency.sos.button.utils.PermissionManager

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var emergencyManager: EmergencyManager
    private lateinit var permissionManager: PermissionManager
    private var countdownTimer: CountDownTimer? = null
    private var isEmergencyActive = false
    private var hasNavigatedToSettings = false
    
    // Permission launcher
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            emergencyManager.initialize()
        }
        // Navigate to Settings regardless of permission status
        if (!hasNavigatedToSettings) {
            hasNavigatedToSettings = true
            navigateToSettings()
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeManagers()
        setupUI()
        checkPermissions()
    }
    
    private fun initializeManagers() {
        emergencyManager = EmergencyManager(this)
        permissionManager = PermissionManager(this)
    }
    
    private fun setupUI() {
        // Emergency button click
        binding.emergencyButton.setOnClickListener {
            if (!isEmergencyActive) {
                startEmergencyCountdown()
            }
        }
        
        // Cancel button click
        binding.cancelButton.setOnClickListener {
            cancelEmergency()
        }
    }
    
    private fun checkPermissions() {
        val requiredPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS
        )
        
        val permissionsToRequest = requiredPermissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        
        if (permissionsToRequest.isNotEmpty()) {
            // Request permissions - will navigate to Settings after (granted or denied)
            permissionLauncher.launch(permissionsToRequest)
        } else {
            // All permissions already granted - navigate to Settings
            emergencyManager.initialize()
            if (!hasNavigatedToSettings) {
                hasNavigatedToSettings = true
                navigateToSettings()
            }
        }
    }
    
    private fun navigateToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        // Don't finish MainActivity so user can return to emergency button
    }
    
    private fun startEmergencyCountdown() {
        if (!emergencyManager.hasEmergencyContacts()) {
            Toast.makeText(this, R.string.error_no_contacts, Toast.LENGTH_LONG).show()
            navigateToSettings()
            return
        }
        
        // Get emergency options from preferences
        val emergencyPreferences = com.emergency.sos.button.utils.EmergencyPreferences(this)
        val sendSMS = emergencyPreferences.getEmergencySendSMS()
        val makeCall = emergencyPreferences.getEmergencyMakeCall()
        
        // Direct emergency execution with saved options
        startCountdownWithOptions(sendSMS = sendSMS, makeCall = makeCall)
    }
    
    private fun showEmergencyOptionsDialog() {
        val options = arrayOf("SMS + Call", "SMS Only", "Call Only")
        
        AlertDialog.Builder(this)
            .setTitle("Emergency Options")
            .setMessage("Choose how to send the emergency alert:")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> {
                        startCountdownWithOptions(sendSMS = true, makeCall = true)
                    }
                    1 -> {
                        startCountdownWithOptions(sendSMS = true, makeCall = false)
                    }
                    2 -> {
                        startCountdownWithOptions(sendSMS = false, makeCall = true)
                    }
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                // User cancelled emergency options
            }
            .show()
    }
    
    private fun startCountdownWithOptions(sendSMS: Boolean, makeCall: Boolean) {
        isEmergencyActive = true
        binding.emergencyButton.isEnabled = false
        binding.countdownText.visibility = View.VISIBLE
        binding.cancelButton.visibility = View.VISIBLE
        
        val actionText = when {
            sendSMS && makeCall -> "SMS + Call"
            sendSMS -> "SMS Only"
            makeCall -> "Call Only"
            else -> "Emergency"
        }
        
        countdownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt()
                binding.countdownText.text = "$actionText in $secondsLeft seconds"
            }
            
            override fun onFinish() {
                executeEmergencyWithOptions(sendSMS, makeCall)
            }
        }.start()
    }
    
    private fun cancelEmergency() {
        countdownTimer?.cancel()
        resetEmergencyUI()
    }
    
    private fun executeEmergency() {
        executeEmergencyWithOptions(sendSMS = true, makeCall = true)
    }
    
    private fun executeEmergencyWithOptions(sendSMS: Boolean, makeCall: Boolean) {
        val actionText = when {
            sendSMS && makeCall -> "Sending SMS + Call..."
            sendSMS -> "Sending SMS..."
            makeCall -> "Making call..."
            else -> "Sending emergency..."
        }
        binding.countdownText.text = actionText
        
        emergencyManager.executeEmergencyWithOptions({ success ->
            runOnUiThread {
                if (success) {
                    val successText = when {
                        sendSMS && makeCall -> "Emergency SMS and call sent!"
                        sendSMS -> "Emergency SMS sent!"
                        makeCall -> "Emergency call made!"
                        else -> "Emergency sent!"
                    }
                    Toast.makeText(this, successText, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, R.string.emergency_failed, Toast.LENGTH_LONG).show()
                }
                resetEmergencyUI()
            }
        }, sendSMS, makeCall)
    }
    
    private fun resetEmergencyUI() {
        isEmergencyActive = false
        binding.emergencyButton.isEnabled = true
        binding.countdownText.visibility = View.GONE
        binding.cancelButton.visibility = View.GONE
        countdownTimer = null
    }
    
    
    
    override fun onResume() {
        super.onResume()
        // Check permissions again when returning to this activity
        checkPermissions()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }
}
