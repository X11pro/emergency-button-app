package com.emergency.button.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionManager(private val context: Context) {
    
    private val TAG = "PermissionManager"
    
    companion object {
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.CAMERA,
            Manifest.permission.VIBRATE
        )
    }
    
    fun hasAllPermissions(): Boolean {
        return REQUIRED_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    fun hasLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    
    fun hasSMSPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED
    }
    
    fun hasPhonePermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    }
    
    fun hasContactsPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
    }
    
    fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    
    fun hasVibratePermission(): Boolean {
        return ContextCompat.checkSelfPermission(context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED
    }
    
    fun getMissingPermissions(): Array<String> {
        return REQUIRED_PERMISSIONS.filter { permission ->
            ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
    }
    
    fun getPermissionStatus(): Map<String, Boolean> {
        return REQUIRED_PERMISSIONS.associate { permission ->
            permission to (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
        }
    }
    
    fun getCriticalPermissions(): Array<String> {
        return arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.SEND_SMS,
            Manifest.permission.CALL_PHONE
        )
    }
    
    fun hasCriticalPermissions(): Boolean {
        return getCriticalPermissions().all { permission ->
            ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }
    
    fun getPermissionDescription(permission: String): String {
        return when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> "Location access is required to send your exact location in emergencies"
            Manifest.permission.ACCESS_COARSE_LOCATION -> "Location access is required to send your approximate location in emergencies"
            Manifest.permission.SEND_SMS -> "SMS permission is required to send emergency messages to your contacts"
            Manifest.permission.CALL_PHONE -> "Phone permission is required to make emergency calls"
            Manifest.permission.READ_CONTACTS -> "Contacts permission is required to select emergency contacts"
            Manifest.permission.CAMERA -> "Camera permission is required to activate the flashlight as an emergency signal"
            Manifest.permission.VIBRATE -> "Vibration permission is required to activate vibration alerts"
            else -> "This permission is required for emergency functionality"
        }
    }
    
    fun getPermissionTitle(permission: String): String {
        return when (permission) {
            Manifest.permission.ACCESS_FINE_LOCATION -> "Location Permission"
            Manifest.permission.ACCESS_COARSE_LOCATION -> "Location Permission"
            Manifest.permission.SEND_SMS -> "SMS Permission"
            Manifest.permission.CALL_PHONE -> "Phone Permission"
            Manifest.permission.READ_CONTACTS -> "Contacts Permission"
            Manifest.permission.CAMERA -> "Camera Permission"
            Manifest.permission.VIBRATE -> "Vibration Permission"
            else -> "Permission Required"
        }
    }
}
