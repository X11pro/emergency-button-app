package com.emergency.button.utils;

import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraManager;
import android.location.Location;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.CancellationToken;
import com.google.android.gms.tasks.CancellationTokenSource;
import com.google.android.gms.tasks.OnTokenCanceledListener;
import kotlinx.coroutines.*;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\u001a\u0010\u0016\u001a\u00020\u00122\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00120\u0018J\u0010\u0010\u0019\u001a\u0004\u0018\u00010\nH\u0082@\u00a2\u0006\u0002\u0010\u001aJ\u0006\u0010\u001b\u001a\u00020\u0010J\u0006\u0010\u001c\u001a\u00020\u0012J\b\u0010\u001d\u001a\u00020\u0010H\u0002J\u001a\u0010\u001e\u001a\u00020\u00102\b\u0010\u001f\u001a\u0004\u0018\u00010\n2\u0006\u0010 \u001a\u00020\u0006H\u0002J\u001a\u0010!\u001a\u00020\u00122\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00120\u0018J\u001a\u0010\"\u001a\u00020\u00122\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00120\u0018R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006#"}, d2 = {"Lcom/emergency/button/utils/EmergencyManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "contactManager", "Lcom/emergency/button/utils/ContactManager;", "currentLocation", "Landroid/location/Location;", "emergencyPreferences", "Lcom/emergency/button/utils/EmergencyPreferences;", "fusedLocationClient", "Lcom/google/android/gms/location/FusedLocationProviderClient;", "isInitialized", "", "activateFlashlight", "", "activatePhysicalAlerts", "activateSoundAlert", "activateVibration", "executeEmergency", "callback", "Lkotlin/Function1;", "getCurrentLocation", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasEmergencyContacts", "initialize", "makeEmergencyCall", "sendEmergencySMS", "location", "message", "testEmergency", "testLocation", "app_debug"})
public final class EmergencyManager {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String TAG = "EmergencyManager";
    @org.jetbrains.annotations.NotNull()
    private final com.google.android.gms.location.FusedLocationProviderClient fusedLocationClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.emergency.button.utils.EmergencyPreferences emergencyPreferences = null;
    @org.jetbrains.annotations.NotNull()
    private final com.emergency.button.utils.ContactManager contactManager = null;
    private boolean isInitialized = false;
    @org.jetbrains.annotations.Nullable()
    private android.location.Location currentLocation;
    
    public EmergencyManager(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void initialize() {
    }
    
    public final boolean hasEmergencyContacts() {
        return false;
    }
    
    public final void executeEmergency(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> callback) {
    }
    
    private final java.lang.Object getCurrentLocation(kotlin.coroutines.Continuation<? super android.location.Location> $completion) {
        return null;
    }
    
    private final boolean sendEmergencySMS(android.location.Location location, java.lang.String message) {
        return false;
    }
    
    private final boolean makeEmergencyCall() {
        return false;
    }
    
    private final void activatePhysicalAlerts() {
    }
    
    private final void activateVibration() {
    }
    
    private final void activateFlashlight() {
    }
    
    private final void activateSoundAlert() {
    }
    
    public final void testEmergency(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.Boolean, kotlin.Unit> callback) {
    }
    
    public final void testLocation(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> callback) {
    }
}