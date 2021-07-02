package com.ang.acb.addressbook.domain

import kotlinx.coroutines.flow.Flow

interface ContactGateway {
    suspend fun saveContact(contact: Contact): Long
    fun getContact(contactId: Long): Flow<Contact?>
    fun getContacts(): Flow<List<Contact>>
}
