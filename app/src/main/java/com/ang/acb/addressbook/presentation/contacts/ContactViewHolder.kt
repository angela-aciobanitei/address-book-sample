package com.ang.acb.addressbook.presentation.contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ang.acb.addressbook.databinding.ItemContactBinding
import com.ang.acb.addressbook.domain.Contact

class ContactViewHolder(
    private val binding: ItemContactBinding,
    val onContactClick: (id: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(contact: Contact) {
        binding.tvName.text = "${contact.firstName} ${contact.lastName}"
        binding.tvPhoneNumber.text = contact.phoneNumber
        binding.contactRoot.setOnClickListener {
            onContactClick(contact.id)
        }
    }

    companion object {
        fun create(parent: ViewGroup, onContactClick: (id: Long) -> Unit): ContactViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemContactBinding.inflate(inflater, parent, false)

            return ContactViewHolder(binding, onContactClick)
        }
    }
}
