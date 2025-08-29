package com.emergency.button.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class EmergencyContact(
    val name: String,
    val phoneNumber: String,
    val id: String = System.currentTimeMillis().toString()
)

class ContactManager(private val context: Context) {
    
    private val TAG = "ContactManager"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("emergency_contacts", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val contactsKey = "emergency_contacts_list"
    
    fun getEmergencyContacts(): List<EmergencyContact> {
        return try {
            val contactsJson = sharedPreferences.getString(contactsKey, "[]")
            val type = object : TypeToken<List<EmergencyContact>>() {}.type
            gson.fromJson(contactsJson, type) ?: emptyList()
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error loading contacts", e)
            emptyList()
        }
    }
    
    fun addEmergencyContact(contact: EmergencyContact): Boolean {
        return try {
            val currentContacts = getEmergencyContacts().toMutableList()
            
            // Check if contact already exists
            if (currentContacts.any { it.phoneNumber == contact.phoneNumber }) {
                android.util.Log.w(TAG, "Contact already exists: ${contact.phoneNumber}")
                return false
            }
            
            // Limit to 3 contacts
            if (currentContacts.size >= 3) {
                android.util.Log.w(TAG, "Maximum 3 emergency contacts allowed")
                return false
            }
            
            currentContacts.add(contact)
            saveContacts(currentContacts)
            android.util.Log.d(TAG, "Contact added: ${contact.name}")
            true
            
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error adding contact", e)
            false
        }
    }
    
    fun removeEmergencyContact(contactId: String): Boolean {
        return try {
            val currentContacts = getEmergencyContacts().toMutableList()
            val removed = currentContacts.removeAll { it.id == contactId }
            
            if (removed) {
                saveContacts(currentContacts)
                android.util.Log.d(TAG, "Contact removed: $contactId")
            }
            
            removed
            
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error removing contact", e)
            false
        }
    }
    
    fun updateEmergencyContact(contact: EmergencyContact): Boolean {
        return try {
            val currentContacts = getEmergencyContacts().toMutableList()
            val index = currentContacts.indexOfFirst { it.id == contact.id }
            
            if (index != -1) {
                currentContacts[index] = contact
                saveContacts(currentContacts)
                android.util.Log.d(TAG, "Contact updated: ${contact.name}")
                true
            } else {
                android.util.Log.w(TAG, "Contact not found: ${contact.id}")
                false
            }
            
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error updating contact", e)
            false
        }
    }
    
    fun clearAllContacts() {
        try {
            sharedPreferences.edit().remove(contactsKey).apply()
            android.util.Log.d(TAG, "All contacts cleared")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error clearing contacts", e)
        }
    }
    
    fun getContactCount(): Int {
        return getEmergencyContacts().size
    }
    
    fun hasContacts(): Boolean {
        return getContactCount() > 0
    }
    
    private fun saveContacts(contacts: List<EmergencyContact>) {
        try {
            val contactsJson = gson.toJson(contacts)
            sharedPreferences.edit().putString(contactsKey, contactsJson).apply()
            android.util.Log.d(TAG, "Contacts saved: ${contacts.size} contacts")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Error saving contacts", e)
        }
    }
}
