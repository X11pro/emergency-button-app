package com.emergency.button.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.emergency.button.R
import com.emergency.button.utils.EmergencyManager

class FloatingSOSService : Service() {
    
    private val TAG = "FloatingSOSService"
    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var emergencyManager: EmergencyManager? = null
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "FloatingSOSService created")
        
        emergencyManager = EmergencyManager(this)
        emergencyManager?.initialize()
        
        createFloatingView()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "FloatingSOSService started")
        return START_STICKY
    }
    
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
    
    private fun createFloatingView() {
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        
        val inflater = LayoutInflater.from(this)
        floatingView = inflater.inflate(R.layout.floating_sos_button, null)
        
        val layoutParams = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            },
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        
        layoutParams.gravity = Gravity.TOP or Gravity.END
        layoutParams.x = 0
        layoutParams.y = 100
        
        // Make the view draggable
        setupDragListener()
        
        // Set click listener for SOS button
        setupSOSClickListener()
        
        try {
            windowManager?.addView(floatingView, layoutParams)
            Log.d(TAG, "Floating SOS button added to window")
        } catch (e: Exception) {
            Log.e(TAG, "Error adding floating view", e)
            Toast.makeText(this, "Error showing SOS button", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun setupDragListener() {
        floatingView?.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        val layoutParams = floatingView?.layoutParams as WindowManager.LayoutParams
                        initialX = layoutParams.x
                        initialY = layoutParams.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val layoutParams = floatingView?.layoutParams as WindowManager.LayoutParams
                        layoutParams.x = initialX + (event.rawX - initialTouchX).toInt()
                        layoutParams.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager?.updateViewLayout(floatingView, layoutParams)
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        // Check if it was a tap (not a drag)
                        val deltaX = Math.abs(event.rawX - initialTouchX)
                        val deltaY = Math.abs(event.rawY - initialTouchY)
                        if (deltaX < 10 && deltaY < 10) {
                            // It was a tap, trigger SOS
                            triggerSOS()
                        }
                        return true
                    }
                }
                return false
            }
        })
    }
    
    private fun setupSOSClickListener() {
        // The click listener is handled in the drag listener above
        // This method is here for future enhancements if needed
    }
    
    private fun triggerSOS() {
        Log.d(TAG, "SOS button pressed - triggering emergency")
        
        if (emergencyManager?.hasEmergencyContacts() == true) {
            // For floating SOS, use SMS + Call by default
            emergencyManager?.executeEmergencyWithOptions({ success ->
                if (success) {
                    Log.d(TAG, "Emergency executed successfully")
                    // Show toast on main thread
                    android.os.Handler(android.os.Looper.getMainLooper()).post {
                        Toast.makeText(this@FloatingSOSService, "Emergency SMS and call sent!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e(TAG, "Emergency execution failed")
                    // Show toast on main thread
                    android.os.Handler(android.os.Looper.getMainLooper()).post {
                        Toast.makeText(this@FloatingSOSService, "Failed to send emergency", Toast.LENGTH_SHORT).show()
                    }
                }
            }, sendSMS = true, makeCall = true)
        } else {
            Log.w(TAG, "No emergency contacts available")
            // Show toast on main thread
            android.os.Handler(android.os.Looper.getMainLooper()).post {
                Toast.makeText(this@FloatingSOSService, "No emergency contacts configured", Toast.LENGTH_LONG).show()
            }
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "FloatingSOSService destroyed")
        
        if (floatingView != null && windowManager != null) {
            try {
                windowManager?.removeView(floatingView)
                Log.d(TAG, "Floating SOS button removed from window")
            } catch (e: Exception) {
                Log.e(TAG, "Error removing floating view", e)
            }
        }
    }
    
    companion object {
        fun isOverlayPermissionGranted(context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Settings.canDrawOverlays(context)
            } else {
                true
            }
        }
        
        fun requestOverlayPermission(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
    }
}
