package com.ang.acb.addressbook.domain

import kotlinx.coroutines.flow.Flow

interface ContactGateway {
    suspend fun saveContact(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        address: String,
    ): Long

    fun getContact(contactId: Long): Flow<Contact?>
    fun getContacts(): Flow<List<Contact>>
}
