package com.emergency.button;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.emergency.button.adapter.ContactsAdapter;
import com.emergency.button.databinding.ActivitySettingsBinding;
import com.emergency.button.utils.ContactManager;
import com.emergency.button.utils.EmergencyContact;
import com.emergency.button.utils.EmergencyManager;
import com.emergency.button.utils.EmergencyPreferences;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0014\u001a\u00020\u0013H\u0002J\u0010\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J\b\u0010\u0018\u001a\u00020\u0011H\u0002J\b\u0010\u0019\u001a\u00020\u0011H\u0002J\b\u0010\u001a\u001a\u00020\u0011H\u0002J\u0012\u0010\u001b\u001a\u00020\u00112\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0014J\b\u0010\u001e\u001a\u00020\u0011H\u0014J\b\u0010\u001f\u001a\u00020 H\u0016J\u0010\u0010!\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020#H\u0002J\b\u0010$\u001a\u00020\u0011H\u0002J\b\u0010%\u001a\u00020\u0011H\u0002J\b\u0010&\u001a\u00020\u0011H\u0002J\b\u0010\'\u001a\u00020\u0011H\u0002J\u0010\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020 H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006*"}, d2 = {"Lcom/emergency/button/SettingsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/emergency/button/databinding/ActivitySettingsBinding;", "contactManager", "Lcom/emergency/button/utils/ContactManager;", "contactPickerLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Ljava/lang/Void;", "contactsAdapter", "Lcom/emergency/button/adapter/ContactsAdapter;", "emergencyManager", "Lcom/emergency/button/utils/EmergencyManager;", "emergencyPreferences", "Lcom/emergency/button/utils/EmergencyPreferences;", "addContact", "", "getContactPhoneNumber", "", "contactId", "handleContactSelection", "uri", "Landroid/net/Uri;", "initializeManagers", "loadContacts", "loadSettings", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onSupportNavigateUp", "", "removeContact", "contact", "Lcom/emergency/button/utils/EmergencyContact;", "saveEmergencyMessage", "setupUI", "testEmergency", "testLocation", "updateTestModeUI", "enabled", "app_debug"})
public final class SettingsActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.emergency.button.databinding.ActivitySettingsBinding binding;
    private com.emergency.button.utils.ContactManager contactManager;
    private com.emergency.button.utils.EmergencyPreferences emergencyPreferences;
    private com.emergency.button.utils.EmergencyManager emergencyManager;
    private com.emergency.button.adapter.ContactsAdapter contactsAdapter;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.Void> contactPickerLauncher = null;
    
    public SettingsActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void initializeManagers() {
    }
    
    private final void setupUI() {
    }
    
    private final void loadSettings() {
    }
    
    private final void loadContacts() {
    }
    
    private final void addContact() {
    }
    
    private final void handleContactSelection(android.net.Uri uri) {
    }
    
    private final java.lang.String getContactPhoneNumber(java.lang.String contactId) {
        return null;
    }
    
    private final void removeContact(com.emergency.button.utils.EmergencyContact contact) {
    }
    
    private final void saveEmergencyMessage() {
    }
    
    private final void testEmergency() {
    }
    
    private final void testLocation() {
    }
    
    private final void updateTestModeUI(boolean enabled) {
    }
    
    @java.lang.Override()
    public boolean onSupportNavigateUp() {
        return false;
    }
    
    @java.lang.Override()
    protected void onPause() {
    }
}