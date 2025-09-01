package com.emergency.button.utils;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015J\u0006\u0010\u0016\u001a\u00020\rJ\u000e\u0010\u0017\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00020\u00112\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0015H\u0002J\u000e\u0010\u001b\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"Lcom/emergency/button/utils/ContactManager;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "TAG", "", "contactsKey", "gson", "Lcom/google/gson/Gson;", "sharedPreferences", "Landroid/content/SharedPreferences;", "addEmergencyContact", "", "contact", "Lcom/emergency/button/utils/EmergencyContact;", "clearAllContacts", "", "getContactCount", "", "getEmergencyContacts", "", "hasContacts", "removeEmergencyContact", "contactId", "saveContacts", "contacts", "updateEmergencyContact", "app_debug"})
public final class ContactManager {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String TAG = "ContactManager";
    @org.jetbrains.annotations.NotNull
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.gson.Gson gson = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String contactsKey = "emergency_contacts_list";
    
    public ContactManager(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.emergency.button.utils.EmergencyContact> getEmergencyContacts() {
        return null;
    }
    
    public final boolean addEmergencyContact(@org.jetbrains.annotations.NotNull
    com.emergency.button.utils.EmergencyContact contact) {
        return false;
    }
    
    public final boolean removeEmergencyContact(@org.jetbrains.annotations.NotNull
    java.lang.String contactId) {
        return false;
    }
    
    public final boolean updateEmergencyContact(@org.jetbrains.annotations.NotNull
    com.emergency.button.utils.EmergencyContact contact) {
        return false;
    }
    
    public final void clearAllContacts() {
    }
    
    public final int getContactCount() {
        return 0;
    }
    
    public final boolean hasContacts() {
        return false;
    }
    
    private final void saveContacts(java.util.List<com.emergency.button.utils.EmergencyContact> contacts) {
    }
}