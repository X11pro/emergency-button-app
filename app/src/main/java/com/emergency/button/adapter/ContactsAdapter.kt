package com.emergency.button.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.emergency.button.R
import com.emergency.button.databinding.ItemContactBinding
import com.emergency.button.utils.EmergencyContact

class ContactsAdapter(
    private val onRemoveClick: (EmergencyContact) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    
    private var contacts: List<EmergencyContact> = emptyList()
    
    fun updateContacts(newContacts: List<EmergencyContact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ContactViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
    
    override fun getItemCount(): Int = contacts.size
    
    inner class ContactViewHolder(
        private val binding: ItemContactBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(contact: EmergencyContact) {
            binding.contactName.text = contact.name
            binding.contactPhone.text = contact.phoneNumber
            
            binding.removeButton.setOnClickListener {
                onRemoveClick(contact)
            }
        }
    }
}
