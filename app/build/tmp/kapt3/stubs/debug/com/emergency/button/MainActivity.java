package com.emergency.button;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0002J\b\u0010\u0013\u001a\u00020\u0012H\u0002J\b\u0010\u0014\u001a\u00020\u0012H\u0002J\b\u0010\u0015\u001a\u00020\u0012H\u0002J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0014J\b\u0010\u0019\u001a\u00020\u0012H\u0014J\b\u0010\u001a\u001a\u00020\u0012H\u0014J\b\u0010\u001b\u001a\u00020\u0012H\u0002J\b\u0010\u001c\u001a\u00020\u0012H\u0002J\b\u0010\u001d\u001a\u00020\u0012H\u0002J\b\u0010\u001e\u001a\u00020\u0012H\u0002J\b\u0010\u001f\u001a\u00020\u0012H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0010X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/emergency/button/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/emergency/button/databinding/ActivityMainBinding;", "countdownTimer", "Landroid/os/CountDownTimer;", "emergencyManager", "Lcom/emergency/button/utils/EmergencyManager;", "isEmergencyActive", "", "permissionLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "permissionManager", "Lcom/emergency/button/utils/PermissionManager;", "cancelEmergency", "", "checkPermissions", "executeEmergency", "initializeManagers", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onResume", "resetEmergencyUI", "setupUI", "showPermissionDeniedDialog", "startEmergencyCountdown", "startSettingsActivity", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.emergency.button.databinding.ActivityMainBinding binding;
    private com.emergency.button.utils.EmergencyManager emergencyManager;
    private com.emergency.button.utils.PermissionManager permissionManager;
    @org.jetbrains.annotations.Nullable
    private android.os.CountDownTimer countdownTimer;
    private boolean isEmergencyActive = false;
    @org.jetbrains.annotations.NotNull
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissionLauncher = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeManagers() {
    }
    
    private final void setupUI() {
    }
    
    private final void checkPermissions() {
    }
    
    private final void startEmergencyCountdown() {
    }
    
    private final void cancelEmergency() {
    }
    
    private final void executeEmergency() {
    }
    
    private final void resetEmergencyUI() {
    }
    
    private final void startSettingsActivity() {
    }
    
    private final void showPermissionDeniedDialog() {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
}