package com.emergency.button

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.emergency.button.adapter.ContactsAdapter
import com.emergency.button.databinding.ActivitySettingsBinding
import com.emergency.button.utils.ContactManager
import com.emergency.button.utils.EmergencyContact
import com.emergency.button.utils.EmergencyManager
import com.emergency.button.utils.EmergencyPreferences

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var contactManager: ContactManager
    private lateinit var emergencyPreferences: EmergencyPreferences
    private lateinit var emergencyManager: EmergencyManager
    private lateinit var contactsAdapter: ContactsAdapter
    
    // Contact picker launcher
    private val contactPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ) { uri ->
        uri?.let { handleContactSelection(it) }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            android.util.Log.d("SettingsActivity", "Starting onCreate")
            binding = ActivitySettingsBinding.inflate(layoutInflater)
            android.util.Log.d("SettingsActivity", "Binding inflated successfully")
            setContentView(binding.root)
            android.util.Log.d("SettingsActivity", "Content view set")
            
            initializeManagers()
            android.util.Log.d("SettingsActivity", "Managers initialized")
            setupUI()
            android.util.Log.d("SettingsActivity", "UI setup completed")
            loadSettings()
            android.util.Log.d("SettingsActivity", "Settings loaded successfully")
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Error initializing settings: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun initializeManagers() {
        try {
            android.util.Log.d("SettingsActivity", "Initializing ContactManager")
            contactManager = ContactManager(this)
            android.util.Log.d("SettingsActivity", "ContactManager initialized")
            
            android.util.Log.d("SettingsActivity", "Initializing EmergencyPreferences")
            emergencyPreferences = EmergencyPreferences(this)
            android.util.Log.d("SettingsActivity", "EmergencyPreferences initialized")
            
            android.util.Log.d("SettingsActivity", "Initializing EmergencyManager")
            emergencyManager = EmergencyManager(this)
            android.util.Log.d("SettingsActivity", "EmergencyManager initialized")
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error initializing managers: ${e.message}", e)
            throw e
        }
    }
    
    private fun setupUI() {
        try {
            // Setup toolbar
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            
            // Setup contacts recycler view
            contactsAdapter = ContactsAdapter(
                onRemoveClick = { contact -> removeContact(contact) }
            )
            binding.contactsRecyclerView.apply {
                layoutManager = LinearLayoutManager(this@SettingsActivity)
                adapter = contactsAdapter
            }
            
            // Setup button click listeners
            binding.addContactButton.setOnClickListener {
                addContact()
            }
            
            binding.testEmergencyButton.setOnClickListener {
                testEmergency()
            }
            
            binding.testModeSwitch.setOnCheckedChangeListener { _, isChecked ->
                emergencyPreferences.setTestMode(isChecked)
                updateTestModeUI(isChecked)
            }
            
            // Setup emergency message input
            binding.emergencyMessageInput.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    saveEmergencyMessage()
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error setting up UI", e)
            throw e
        }
    }
    
    private fun loadSettings() {
        try {
            // Load emergency message
            val message = emergencyPreferences.getEmergencyMessage()
            binding.emergencyMessageInput.setText(message)
            
            // Load contacts
            loadContacts()
            
            // Load test mode
            val testMode = emergencyPreferences.isTestModeEnabled()
            binding.testModeSwitch.isChecked = testMode
            updateTestModeUI(testMode)
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error loading settings", e)
            Toast.makeText(this, "Error loading settings", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun loadContacts() {
        val contacts = contactManager.getEmergencyContacts()
        contactsAdapter.updateContacts(contacts)
    }
    
    private fun addContact() {
        if (contactManager.getContactCount() >= 3) {
            Toast.makeText(this, "Maximum 3 emergency contacts allowed", Toast.LENGTH_SHORT).show()
            return
        }
        
        contactPickerLauncher.launch(null)
    }
    
    private fun handleContactSelection(uri: android.net.Uri) {
        try {
            val cursor = contentResolver.query(
                uri,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
                ),
                null,
                null,
                null
            )
            
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    
                    val name = it.getString(nameIndex)
                    val phoneNumber = it.getString(numberIndex)?.replace("\\s".toRegex(), "")
                    
                    if (phoneNumber != null) {
                        val contact = EmergencyContact(name, phoneNumber)
                        if (contactManager.addEmergencyContact(contact)) {
                            loadContacts()
                            Toast.makeText(this, R.string.contact_added, Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Contact already exists", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Error selecting contact", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun removeContact(contact: EmergencyContact) {
        AlertDialog.Builder(this)
            .setTitle("Remove Contact")
            .setMessage("Are you sure you want to remove ${contact.name} from emergency contacts?")
            .setPositiveButton(R.string.delete) { _, _ ->
                if (contactManager.removeEmergencyContact(contact.id)) {
                    loadContacts()
                    Toast.makeText(this, R.string.contact_removed, Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun saveEmergencyMessage() {
        val message = binding.emergencyMessageInput.text.toString().trim()
        if (message.isNotEmpty()) {
            emergencyPreferences.setEmergencyMessage(message)
            Toast.makeText(this, R.string.settings_saved, Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun testEmergency() {
        if (!emergencyManager.hasEmergencyContacts()) {
            Toast.makeText(this, R.string.error_no_contacts, Toast.LENGTH_LONG).show()
            return
        }
        
        AlertDialog.Builder(this)
            .setTitle(R.string.confirm_test)
            .setMessage(R.string.confirm_test_message)
            .setPositiveButton(R.string.yes) { _, _ ->
                emergencyManager.testEmergency { success ->
                    runOnUiThread {
                        if (success) {
                            Toast.makeText(this, R.string.test_successful, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, R.string.emergency_failed, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }
    
    private fun updateTestModeUI(enabled: Boolean) {
        val statusText = if (enabled) R.string.test_mode_enabled else R.string.test_mode_disabled
        Toast.makeText(this, statusText, Toast.LENGTH_SHORT).show()
    }
    
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    
    override fun onPause() {
        super.onPause()
        saveEmergencyMessage()
    }
}
