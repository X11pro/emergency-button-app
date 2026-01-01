package com.emergency.sos.button

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.emergency.sos.button.databinding.ActivityWidgetConfigurationBinding
import com.emergency.sos.button.utils.EmergencyManager
import com.emergency.sos.button.utils.EmergencyPreferences
import com.emergency.sos.button.utils.PermissionManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import java.util.Locale

class WidgetConfigurationActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityWidgetConfigurationBinding
    private lateinit var emergencyManager: EmergencyManager
    private lateinit var emergencyPreferences: EmergencyPreferences
    private lateinit var permissionManager: PermissionManager
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    
    private var locationUpdateJob: Job? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityWidgetConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initializeManagers()
        setupUI()
        loadSettings()
        updateLocation()
    }
    
    private fun initializeManagers() {
        emergencyManager = EmergencyManager(this)
        emergencyPreferences = EmergencyPreferences(this)
        permissionManager = PermissionManager(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }
    
    private fun setupUI() {
        // Settings button (can navigate back or to main settings)
        binding.settingsButton.setOnClickListener {
            finish()
        }
        
        // Auto-Share Location toggle
        binding.autoShareLocationSwitch.setOnCheckedChangeListener { _, isChecked ->
            emergencyPreferences.setWidgetAutoShareLocation(isChecked)
            Toast.makeText(
                this,
                if (isChecked) "Auto-Share Location enabled" else "Auto-Share Location disabled",
                Toast.LENGTH_SHORT
            ).show()
        }
        
        // Silent Alarm toggle
        binding.silentAlarmSwitch.setOnCheckedChangeListener { _, isChecked ->
            emergencyPreferences.setWidgetSilentAlarm(isChecked)
            Toast.makeText(
                this,
                if (isChecked) "Silent Alarm enabled" else "Silent Alarm disabled",
                Toast.LENGTH_SHORT
            ).show()
        }
        
        // Details Button (in widget preview)
        binding.detailsButton.setOnClickListener {
            // Could navigate to details screen or show info dialog
            Toast.makeText(this, "Details feature", Toast.LENGTH_SHORT).show()
        }
        
        // Trigger SOS Button
        binding.triggerSOSButton.setOnClickListener {
            triggerSOS()
        }
    }
    
    private fun loadSettings() {
        // Load toggle states
        binding.autoShareLocationSwitch.isChecked = 
            emergencyPreferences.getWidgetAutoShareLocation()
        binding.silentAlarmSwitch.isChecked = 
            emergencyPreferences.getWidgetSilentAlarm()
        
        // Update permission status
        updatePermissionStatus()
    }
    
    private fun updatePermissionStatus() {
        val hasAllPermissions = permissionManager.hasAllPermissions()
        val hasSomePermissions = permissionManager.getPermissionStatus().values.any { it }
        
        val statusColor = when {
            hasAllPermissions -> android.graphics.Color.parseColor("#4CAF50") // Green
            hasSomePermissions -> android.graphics.Color.parseColor("#FF9800") // Yellow/Orange
            else -> android.graphics.Color.parseColor("#F44336") // Red
        }
        
        binding.readyStatusDot.backgroundTintList = android.content.res.ColorStateList.valueOf(statusColor)
    }
    
    private fun updateLocation() {
        locationUpdateJob = CoroutineScope(Dispatchers.IO).launch {
            try {
                val location = withContext(Dispatchers.IO) {
                    emergencyManager.getCurrentLocationSync()
                }
                if (location != null) {
                    val address = getAddressFromLocation(location.latitude, location.longitude)
                    withContext(Dispatchers.Main) {
                        binding.widgetLocationText.text = address ?: "Location available"
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.widgetLocationText.text = "Location unavailable"
                    }
                }
            } catch (e: Exception) {
                android.util.Log.e("WidgetConfig", "Error updating location", e)
                withContext(Dispatchers.Main) {
                    binding.widgetLocationText.text = "Location unavailable"
                }
            }
        }
    }
    
    private suspend fun getAddressFromLocation(lat: Double, lon: Double): String? = withContext(Dispatchers.IO) {
        return@withContext try {
            val geocoder = Geocoder(this@WidgetConfigurationActivity, Locale.getDefault())
            val addresses: List<Address>? = geocoder.getFromLocation(lat, lon, 1)
            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]
                val city = address.locality ?: ""
                val state = address.adminArea ?: ""
                if (city.isNotEmpty() && state.isNotEmpty()) {
                    "$city, $state"
                } else if (city.isNotEmpty()) {
                    city
                } else {
                    null
                }
            } else {
                null
            }
        } catch (e: Exception) {
            android.util.Log.e("WidgetConfig", "Error geocoding location", e)
            null
        }
    }
    
    private fun triggerSOS() {
        if (!emergencyManager.hasEmergencyContacts()) {
            Toast.makeText(this, "No emergency contacts configured", Toast.LENGTH_LONG).show()
            return
        }
        
        // Get widget-specific options
        val sendSMS = emergencyPreferences.getWidgetSendSMS()
        val makeCall = emergencyPreferences.getWidgetMakeCall()
        val silentAlarm = emergencyPreferences.getWidgetSilentAlarm()
        
        emergencyManager.executeEmergencyWithOptions({ success ->
            runOnUiThread {
                if (success) {
                    val actionText = when {
                        sendSMS && makeCall -> "Emergency SMS and call sent!"
                        sendSMS -> "Emergency SMS sent!"
                        makeCall -> "Emergency call made!"
                        else -> "Emergency sent!"
                    }
                    Toast.makeText(this, actionText, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Emergency failed", Toast.LENGTH_LONG).show()
                }
            }
        }, sendSMS, makeCall)
    }
    
    override fun onResume() {
        super.onResume()
        // Update permission status when returning to the screen
        updatePermissionStatus()
        // Update location
        updateLocation()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        locationUpdateJob?.cancel()
    }
}

