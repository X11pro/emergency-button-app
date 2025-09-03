package com.emergency.button.utils;

import android.content.Context;
import android.content.SharedPreferences;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u000b\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\nJ\u0006\u0010\u000b\u001a\u00020\u0006J\u0006\u0010\f\u001a\u00020\rJ\u0006\u0010\u000e\u001a\u00020\rJ\u0006\u0010\u000f\u001a\u00020\rJ\u0006\u0010\u0010\u001a\u00020\rJ\u0006\u0010\u0011\u001a\u00020\rJ\u0006\u0010\u0012\u001a\u00020\rJ\u0006\u0010\u0013\u001a\u00020\u0014J\u000e\u0010\u0015\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u0006J\u0006\u0010\u001a\u001a\u00020\rJ\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u001c\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u001d\u001a\u00020\r2\u0006\u0010\u0016\u001a\u00020\rR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/emergency/button/utils/EmergencyPreferences;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "sharedPreferences", "Landroid/content/SharedPreferences;", "getAllSettings", "", "getEmergencyMessage", "isAutoCallEnabled", "", "isAutoSMSEnabled", "isFirstRun", "isPhysicalAlertsEnabled", "isTestModeEnabled", "isWidgetEnabled", "resetToDefaults", "", "setAutoCallEnabled", "enabled", "setAutoSMSEnabled", "setEmergencyMessage", "message", "setFirstRunComplete", "setPhysicalAlertsEnabled", "setTestMode", "setWidgetEnabled", "Companion", "app_debug"})
public final class EmergencyPreferences {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "EmergencyPreferences";
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EMERGENCY_MESSAGE = "emergency_message";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_TEST_MODE = "test_mode";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FIRST_RUN = "first_run";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_WIDGET_ENABLED = "widget_enabled";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AUTO_CALL = "auto_call";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_AUTO_SMS = "auto_sms";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_PHYSICAL_ALERTS = "physical_alerts";
    @org.jetbrains.annotations.NotNull()
    public static final com.emergency.button.utils.EmergencyPreferences.Companion Companion = null;
    
    public EmergencyPreferences(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getEmergencyMessage() {
        return null;
    }
    
    public final boolean setEmergencyMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
        return false;
    }
    
    public final boolean isTestModeEnabled() {
        return false;
    }
    
    public final boolean setTestMode(boolean enabled) {
        return false;
    }
    
    public final boolean isFirstRun() {
        return false;
    }
    
    public final boolean setFirstRunComplete() {
        return false;
    }
    
    public final boolean isWidgetEnabled() {
        return false;
    }
    
    public final boolean setWidgetEnabled(boolean enabled) {
        return false;
    }
    
    public final boolean isAutoCallEnabled() {
        return false;
    }
    
    public final boolean setAutoCallEnabled(boolean enabled) {
        return false;
    }
    
    public final boolean isAutoSMSEnabled() {
        return false;
    }
    
    public final boolean setAutoSMSEnabled(boolean enabled) {
        return false;
    }
    
    public final boolean isPhysicalAlertsEnabled() {
        return false;
    }
    
    public final boolean setPhysicalAlertsEnabled(boolean enabled) {
        return false;
    }
    
    public final void resetToDefaults() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<java.lang.String, java.lang.Object> getAllSettings() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/emergency/button/utils/EmergencyPreferences$Companion;", "", "()V", "KEY_AUTO_CALL", "", "KEY_AUTO_SMS", "KEY_EMERGENCY_MESSAGE", "KEY_FIRST_RUN", "KEY_PHYSICAL_ALERTS", "KEY_TEST_MODE", "KEY_WIDGET_ENABLED", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}