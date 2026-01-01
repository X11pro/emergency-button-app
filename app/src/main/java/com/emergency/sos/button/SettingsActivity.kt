package com.emergency.sos.button

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.emergency.sos.button.adapter.ContactsAdapter
import com.emergency.sos.button.databinding.ActivitySettingsBinding
import com.emergency.sos.button.utils.ContactManager
import com.emergency.sos.button.utils.EmergencyContact
import com.emergency.sos.button.utils.EmergencyManager
import com.emergency.sos.button.utils.EmergencyPreferences

class SettingsActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var contactManager: ContactManager
    private lateinit var emergencyPreferences: EmergencyPreferences
    private lateinit var emergencyManager: EmergencyManager
    private lateinit var contactsAdapter: ContactsAdapter
    
    private var selectedActivationMode = 2 // 0: SMS Only, 1: Call Only, 2: SMS + Call
    
    // Contact picker launcher
    private val contactPickerLauncher = registerForActivityResult(
        ActivityResultContracts.PickContact()
    ) { uri ->
        uri?.let { handleContactSelection(it) }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        try {
            binding = ActivitySettingsBinding.inflate(layoutInflater)
            setContentView(binding.root)
            
            initializeManagers()
            setupUI()
            loadSettings()
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error in onCreate: ${e.message}", e)
            Toast.makeText(this, "Error initializing settings: ${e.message}", Toast.LENGTH_LONG).show()
            finish()
        }
    }
    
    private fun initializeManagers() {
        contactManager = ContactManager(this)
        emergencyPreferences = EmergencyPreferences(this)
        emergencyManager = EmergencyManager(this)
    }
    
    private fun setupUI() {
        // Toolbar title is now a TextView, no action bar needed
        
        // Setup contacts recycler view
        contactsAdapter = ContactsAdapter(
            onRemoveClick = { contact -> removeContact(contact) }
        )
        binding.contactsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@SettingsActivity)
            adapter = contactsAdapter
        }
        
        // Activation Mode Segmented Control
        setupActivationModeButtons()
        
        // Emergency Message with Character Counter
        setupMessageInput()
        
        // Add Contact Button
        binding.addContactButton.setOnClickListener {
            addContact()
        }
        
        // System Check Buttons
        binding.testSirenCard.setOnClickListener {
            testSiren()
        }
        
        binding.testGPSCard.setOnClickListener {
            testGPS()
        }
        
        // Widget Configuration Button
        binding.widgetConfigurationButton.setOnClickListener {
            navigateToWidgetConfiguration()
        }
        
        // Save Changes Button
        binding.saveChangesButton.setOnClickListener {
            saveAllChanges()
        }
    }
    
    private fun setupActivationModeButtons() {
        // Set click listeners for segmented control
        binding.activationModeSMSOnly.setOnClickListener {
            selectActivationMode(0)
        }
        
        binding.activationModeCallOnly.setOnClickListener {
            selectActivationMode(1)
        }
        
        binding.activationModeSMSAndCall.setOnClickListener {
            selectActivationMode(2)
        }
    }
    
    private fun selectActivationMode(mode: Int) {
        selectedActivationMode = mode
        
        // Reset all buttons
        resetActivationModeButtons()
        
            // Highlight selected button
            when (mode) {
                0 -> { // SMS Only
                    binding.activationModeSMSOnly.apply {
                        setTextColor(ContextCompat.getColor(this@SettingsActivity, R.color.primary))
                        setBackgroundColor(ContextCompat.getColor(this@SettingsActivity, R.color.surface_light))
                    }
                }
                1 -> { // Call Only
                    binding.activationModeCallOnly.apply {
                        setTextColor(ContextCompat.getColor(this@SettingsActivity, R.color.primary))
                        setBackgroundColor(ContextCompat.getColor(this@SettingsActivity, R.color.surface_light))
                    }
                }
                2 -> { // SMS + Call
                    binding.activationModeSMSAndCall.apply {
                        setTextColor(ContextCompat.getColor(this@SettingsActivity, R.color.primary))
                        setBackgroundColor(ContextCompat.getColor(this@SettingsActivity, R.color.surface_light))
                    }
                }
            }
    }
    
    private fun resetActivationModeButtons() {
        val defaultTextColor = ContextCompat.getColor(this, R.color.text_secondary_dark)
        val defaultBgColor = android.graphics.Color.TRANSPARENT
        
        binding.activationModeSMSOnly.apply {
            setTextColor(defaultTextColor)
            setBackgroundColor(defaultBgColor)
        }
        
        binding.activationModeCallOnly.apply {
            setTextColor(defaultTextColor)
            setBackgroundColor(defaultBgColor)
        }
        
        binding.activationModeSMSAndCall.apply {
            setTextColor(defaultTextColor)
            setBackgroundColor(defaultBgColor)
        }
    }
    
    private fun setupMessageInput() {
        binding.emergencyMessageInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateCharacterCount(s?.length ?: 0)
            }
            
            override fun afterTextChanged(s: Editable?) {}
        })
    }
    
    private fun updateCharacterCount(length: Int) {
        binding.characterCountText.text = "$length/160"
    }
    
    private fun loadSettings() {
        // Load emergency message
        val message = emergencyPreferences.getEmergencyMessage()
        binding.emergencyMessageInput.setText(message)
        updateCharacterCount(message.length)
        
        // Load contacts
        loadContacts()
        
        // Load activation mode
        val sendSMS = emergencyPreferences.getEmergencySendSMS()
        val makeCall = emergencyPreferences.getEmergencyMakeCall()
        selectedActivationMode = when {
            sendSMS && makeCall -> 2
            sendSMS -> 0
            makeCall -> 1
            else -> 2
        }
        selectActivationMode(selectedActivationMode)
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
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts._ID
                ),
                null,
                null,
                null
            )
            
            cursor?.use {
                if (it.moveToFirst()) {
                    val nameIndex = it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                    val idIndex = it.getColumnIndex(ContactsContract.Contacts._ID)
                    
                    if (nameIndex >= 0 && idIndex >= 0) {
                        val name = it.getString(nameIndex) ?: "Unknown"
                        val contactId = it.getString(idIndex)
                        
                        val phoneNumber = getContactPhoneNumber(contactId)
                        
                        if (!phoneNumber.isNullOrEmpty()) {
                            val contact = EmergencyContact(name, phoneNumber)
                            
                            if (contactManager.addEmergencyContact(contact)) {
                                loadContacts()
                                Toast.makeText(this, "Contact added successfully", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, "Contact already exists or could not be saved", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(this, "No phone number found for this contact", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error selecting contact: ${e.message}", e)
            Toast.makeText(this, "Error selecting contact: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun getContactPhoneNumber(contactId: String): String? {
        return try {
            val phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
                "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
                arrayOf(contactId),
                null
            )
            
            phoneCursor?.use {
                if (it.moveToFirst()) {
                    val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    if (numberIndex >= 0) {
                        it.getString(numberIndex)?.replace("\\s".toRegex(), "")
                    } else null
                } else null
            }
        } catch (e: Exception) {
            android.util.Log.e("SettingsActivity", "Error getting phone number: ${e.message}", e)
            null
        }
    }
    
    private fun removeContact(contact: EmergencyContact) {
        AlertDialog.Builder(this)
            .setTitle("Remove Contact")
            .setMessage("Are you sure you want to remove ${contact.name} from emergency contacts?")
            .setPositiveButton(R.string.delete) { _, _ ->
                if (contactManager.removeEmergencyContact(contact.id)) {
                    loadContacts()
                    Toast.makeText(this, "Contact removed successfully", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.cancel, null)
            .show()
    }
    
    private fun testSiren() {
        Toast.makeText(this, "Testing siren...", Toast.LENGTH_SHORT).show()
        emergencyManager.testEmergency { success ->
            runOnUiThread {
                if (success) {
                    Toast.makeText(this, "Siren test completed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Siren test failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    
    private fun testGPS() {
        Toast.makeText(this, "Testing GPS...", Toast.LENGTH_SHORT).show()
        
        emergencyManager.testLocation { locationResult ->
            runOnUiThread {
                MaterialAlertDialogBuilder(this, R.style.EmergencyDialogStyle)
                    .setTitle("GPS Test Result")
                    .setMessage(locationResult)
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
    
    private fun saveAllChanges() {
        // Save emergency message
        val message = binding.emergencyMessageInput.text.toString().trim()
        if (message.isNotEmpty()) {
            emergencyPreferences.setEmergencyMessage(message)
        }
        
        // Save activation mode
        val (sendSMS, makeCall) = when (selectedActivationMode) {
            0 -> Pair(true, false)  // SMS Only
            1 -> Pair(false, true)  // Call Only
            2 -> Pair(true, true)   // SMS + Call
            else -> Pair(true, true)
        }
        
        val optionName = when (selectedActivationMode) {
            0 -> "SMS Only"
            1 -> "Call Only"
            2 -> "SMS + Call"
            else -> "SMS + Call"
        }
        
        emergencyPreferences.setEmergencyOption(optionName, sendSMS, makeCall)
        
        Toast.makeText(this, "Settings saved successfully", Toast.LENGTH_SHORT).show()
        finish()
    }
    
    private fun navigateToWidgetConfiguration() {
        val intent = Intent(this, WidgetConfigurationActivity::class.java)
        startActivity(intent)
    }
    
    override fun onPause() {
        super.onPause()
        // Auto-save message on pause
        val message = binding.emergencyMessageInput.text.toString().trim()
        if (message.isNotEmpty()) {
            emergencyPreferences.setEmergencyMessage(message)
        }
    }
}
