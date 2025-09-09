package com.emergency.button

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
import com.emergency.button.databinding.ActivityMainBinding
import com.emergency.button.service.FloatingSOSService
import com.emergency.button.utils.EmergencyManager
import com.emergency.button.utils.PermissionManager

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var emergencyManager: EmergencyManager
    private lateinit var permissionManager: PermissionManager
    private var countdownTimer: CountDownTimer? = null
    private var isEmergencyActive = false
    private var isFloatingSOSActive = false
    
    // Permission launcher
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            emergencyManager.initialize()
        } else {
            showPermissionDeniedDialog()
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
        
        // Settings button click
        binding.settingsButton.setOnClickListener {
            startSettingsActivity()
        }
        
        // Floating SOS toggle button click
        binding.floatingSOSToggleButton.setOnClickListener {
            toggleFloatingSOS()
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
            permissionLauncher.launch(permissionsToRequest)
        } else {
            emergencyManager.initialize()
        }
    }
    
    private fun startEmergencyCountdown() {
        if (!emergencyManager.hasEmergencyContacts()) {
            Toast.makeText(this, R.string.error_no_contacts, Toast.LENGTH_LONG).show()
            startSettingsActivity()
            return
        }
        
        isEmergencyActive = true
        binding.emergencyButton.isEnabled = false
        binding.countdownText.visibility = View.VISIBLE
        binding.cancelButton.visibility = View.VISIBLE
        
        countdownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / 1000).toInt()
                binding.countdownText.text = getString(R.string.countdown_text, secondsLeft)
            }
            
            override fun onFinish() {
                executeEmergency()
            }
        }.start()
    }
    
    private fun cancelEmergency() {
        countdownTimer?.cancel()
        resetEmergencyUI()
    }
    
    private fun executeEmergency() {
        binding.countdownText.text = getString(R.string.sending_emergency)
        
        emergencyManager.executeEmergency { success ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this, R.string.emergency_sent, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, R.string.emergency_failed, Toast.LENGTH_LONG).show()
                }
                resetEmergencyUI()
            }
        }
    }
    
    private fun resetEmergencyUI() {
        isEmergencyActive = false
        binding.emergencyButton.isEnabled = true
        binding.countdownText.visibility = View.GONE
        binding.cancelButton.visibility = View.GONE
        countdownTimer = null
    }
    
    private fun startSettingsActivity() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }
    
    private fun toggleFloatingSOS() {
        if (isFloatingSOSActive) {
            stopFloatingSOS()
        } else {
            startFloatingSOS()
        }
    }
    
    private fun startFloatingSOS() {
        if (!FloatingSOSService.isOverlayPermissionGranted(this)) {
            showOverlayPermissionDialog()
            return
        }
        
        val intent = Intent(this, FloatingSOSService::class.java)
        startService(intent)
        isFloatingSOSActive = true
        binding.floatingSOSToggleButton.text = getString(R.string.floating_sos_disabled)
        Toast.makeText(this, R.string.floating_sos_enabled, Toast.LENGTH_SHORT).show()
    }
    
    private fun stopFloatingSOS() {
        val intent = Intent(this, FloatingSOSService::class.java)
        stopService(intent)
        isFloatingSOSActive = false
        binding.floatingSOSToggleButton.text = getString(R.string.floating_sos_toggle_text)
        Toast.makeText(this, R.string.floating_sos_disabled, Toast.LENGTH_SHORT).show()
    }
    
    private fun showOverlayPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.floating_sos_permission_required)
            .setMessage(R.string.floating_sos_permission_message)
            .setPositiveButton(R.string.ok) { _, _ ->
                FloatingSOSService.requestOverlayPermission(this)
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.error_permissions_denied)
            .setMessage("This app requires location, SMS, phone, and contacts permissions to function properly in emergencies.")
            .setPositiveButton(R.string.ok) { _, _ ->
                finish()
            }
            .setCancelable(false)
            .show()
    }
    
    override fun onResume() {
        super.onResume()
        emergencyManager.initialize()
        updateFloatingSOSButtonState()
    }
    
    private fun updateFloatingSOSButtonState() {
        // Check if overlay permission is still granted
        if (!FloatingSOSService.isOverlayPermissionGranted(this)) {
            isFloatingSOSActive = false
            binding.floatingSOSToggleButton.text = getString(R.string.floating_sos_toggle_text)
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        countdownTimer?.cancel()
    }
}
