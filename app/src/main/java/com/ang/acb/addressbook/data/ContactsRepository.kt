package com.ang.acb.addressbook.data

import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.ContactsGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val contactsDataSource: ContactsDataSource,
) : ContactsGateway {

    override suspend fun saveContact(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        address: String,
    ): Long {
        return contactsDataSource.saveContact(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            address = address,
        )
    }

    override fun getContact(contactId: Long): Flow<Contact?> {
        return contactsDataSource.getContact(contactId).map { it?.toContact() }
    }

    override fun getContacts(): Flow<List<Contact>> {
        return contactsDataSource.getAllContacts().map { it.toContacts() }
    }
}
