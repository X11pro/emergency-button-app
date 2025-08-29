package com.emergency.button.utils

import android.content.Context
import android.content.SharedPreferences

class EmergencyPreferences(private val context: Context) {
    
    private val TAG = "EmergencyPreferences"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("emergency_preferences", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_EMERGENCY_MESSAGE = "emergency_message"
        private const val KEY_TEST_MODE = "test_mode"
        private const val KEY_FIRST_RUN = "first_run"
        private const val KEY_WIDGET_ENABLED = "widget_enabled"
        private const val KEY_AUTO_CALL = "auto_call"
        private const val KEY_AUTO_SMS = "auto_sms"
        private const val KEY_PHYSICAL_ALERTS = "physical_alerts"
    }
    
    fun getEmergencyMessage(): String {
        return sharedPreferences.getString(KEY_EMERGENCY_MESSAGE, context.getString(R.string.default_emergency_message)) ?: context.getString(R.string.default_emergency_message)
    }
    
    fun setEmergencyMessage(message: String): Boolean {
        return try {
            sharedPreferences.edit().putString(KEY_EMERGENCY_MESSAGE, message).apply()
            android.util.Log.d(TAG, "Emergency message saved")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error saving emergency message", e)
            false
        }
    }
    
    fun isTestModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_TEST_MODE, false)
    }
    
    fun setTestMode(enabled: Boolean): Boolean {
        return try {
            sharedPreferences.edit().putBoolean(KEY_TEST_MODE, enabled).apply()
            android.util.Log.d(TAG, "Test mode ${if (enabled) "enabled" else "disabled"}")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error setting test mode", e)
            false
        }
    }
    
    fun isFirstRun(): Boolean {
        return sharedPreferences.getBoolean(KEY_FIRST_RUN, true)
    }
    
    fun setFirstRunComplete(): Boolean {
        return try {
            sharedPreferences.edit().putBoolean(KEY_FIRST_RUN, false).apply()
            android.util.Log.d(TAG, "First run completed")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error setting first run", e)
            false
        }
    }
    
    fun isWidgetEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_WIDGET_ENABLED, true)
    }
    
    fun setWidgetEnabled(enabled: Boolean): Boolean {
        return try {
            sharedPreferences.edit().putBoolean(KEY_WIDGET_ENABLED, enabled).apply()
            android.util.Log.d(TAG, "Widget ${if (enabled) "enabled" else "disabled"}")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error setting widget", e)
            false
        }
    }
    
    fun isAutoCallEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_AUTO_CALL, true)
    }
    
    fun setAutoCallEnabled(enabled: Boolean): Boolean {
        return try {
            sharedPreferences.edit().putBoolean(KEY_AUTO_CALL, enabled).apply()
            android.util.Log.d(TAG, "Auto call ${if (enabled) "enabled" else "disabled"}")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error setting auto call", e)
            false
        }
    }
    
    fun isAutoSMSEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_AUTO_SMS, true)
    }
    
    fun setAutoSMSEnabled(enabled: Boolean): Boolean {
        return try {
            sharedPreferences.edit().putBoolean(KEY_AUTO_SMS, enabled).apply()
            android.util.Log.d(TAG, "Auto SMS ${if (enabled) "enabled" else "disabled"}")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error setting auto SMS", e)
            false
        }
    }
    
    fun isPhysicalAlertsEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_PHYSICAL_ALERTS, true)
    }
    
    fun setPhysicalAlertsEnabled(enabled: Boolean): Boolean {
        return try {
            sharedPreferences.edit().putBoolean(KEY_PHYSICAL_ALERTS, enabled).apply()
            android.util.Log.d(TAG, "Physical alerts ${if (enabled) "enabled" else "disabled"}")
            true
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error setting physical alerts", e)
            false
        }
    }
    
    fun resetToDefaults() {
        try {
            sharedPreferences.edit().clear().apply()
            android.util.Log.d(TAG, "Preferences reset to defaults")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error resetting preferences", e)
        }
    }
    
    fun getAllSettings(): Map<String, Any> {
        return mapOf(
            "emergency_message" to getEmergencyMessage(),
            "test_mode" to isTestModeEnabled(),
            "first_run" to isFirstRun(),
            "widget_enabled" to isWidgetEnabled(),
            "auto_call" to isAutoCallEnabled(),
            "auto_sms" to isAutoSMSEnabled(),
            "physical_alerts" to isPhysicalAlertsEnabled()
        )
    }
}
