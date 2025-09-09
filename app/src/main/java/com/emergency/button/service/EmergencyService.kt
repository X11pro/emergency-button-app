package com.emergency.button.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.emergency.button.utils.EmergencyManager

class EmergencyService : Service() {
    
    private val TAG = "EmergencyService"
    private var emergencyManager: EmergencyManager? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "EmergencyService created")
        
        emergencyManager = EmergencyManager(this)
        emergencyManager?.initialize()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "EmergencyService started with action: ${intent?.action}")
        
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
            
            // Use SMS + Call by default for direct emergency
            emergencyManager?.executeEmergencyWithOptions({ success ->
                if (success) {
                    Log.d(TAG, "Emergency executed successfully from service")
                    // Show toast on main thread
                    android.os.Handler(android.os.Looper.getMainLooper()).post {
                        Toast.makeText(this@EmergencyService, "Emergency SMS and call sent!", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e(TAG, "Emergency execution failed from service")
                    // Show toast on main thread
                    android.os.Handler(android.os.Looper.getMainLooper()).post {
                        Toast.makeText(this@EmergencyService, "Failed to send emergency", Toast.LENGTH_LONG).show()
                    }
                }
            }, sendSMS = true, makeCall = true)
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
