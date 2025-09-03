package com.emergency.button.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0005\n\u0002\u0010$\n\u0002\u0010\u000b\n\u0002\b\u000b\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0011\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0002\u0010\tJ\u0011\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006J\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u000f0\u000eJ\u000e\u0010\u0010\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006J\u0006\u0010\u0011\u001a\u00020\u000fJ\u0006\u0010\u0012\u001a\u00020\u000fJ\u0006\u0010\u0013\u001a\u00020\u000fJ\u0006\u0010\u0014\u001a\u00020\u000fJ\u0006\u0010\u0015\u001a\u00020\u000fJ\u0006\u0010\u0016\u001a\u00020\u000fJ\u0006\u0010\u0017\u001a\u00020\u000fJ\u0006\u0010\u0018\u001a\u00020\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/emergency/button/utils/PermissionManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "getCriticalPermissions", "", "()[Ljava/lang/String;", "getMissingPermissions", "getPermissionDescription", "permission", "getPermissionStatus", "", "", "getPermissionTitle", "hasAllPermissions", "hasCameraPermission", "hasContactsPermission", "hasCriticalPermissions", "hasLocationPermission", "hasPhonePermission", "hasSMSPermission", "hasVibratePermission", "Companion", "app_debug"})
public final class PermissionManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "PermissionManager";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String[] REQUIRED_PERMISSIONS = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.SEND_SMS", "android.permission.CALL_PHONE", "android.permission.READ_CONTACTS", "android.permission.CAMERA", "android.permission.VIBRATE"};
    @org.jetbrains.annotations.NotNull()
    public static final com.emergency.button.utils.PermissionManager.Companion Companion = null;
    
    public PermissionManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final boolean hasAllPermissions() {
        return false;
    }
    
    public final boolean hasLocationPermission() {
        return false;
    }
    
    public final boolean hasSMSPermission() {
        return false;
    }
    
    public final boolean hasPhonePermission() {
        return false;
    }
    
    public final boolean hasContactsPermission() {
        return false;
    }
    
    public final boolean hasCameraPermission() {
        return false;
    }
    
    public final boolean hasVibratePermission() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getMissingPermissions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Boolean> getPermissionStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String[] getCriticalPermissions() {
        return null;
    }
    
    public final boolean hasCriticalPermissions() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPermissionDescription(@org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPermissionTitle(@org.jetbrains.annotations.NotNull()
    java.lang.String permission) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0019\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\t"}, d2 = {"Lcom/emergency/button/utils/PermissionManager$Companion;", "", "()V", "REQUIRED_PERMISSIONS", "", "", "getREQUIRED_PERMISSIONS", "()[Ljava/lang/String;", "[Ljava/lang/String;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String[] getREQUIRED_PERMISSIONS() {
            return null;
        }
    }
}