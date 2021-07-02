package com.ang.acb.addressbook.data

import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.ContactGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepository @Inject constructor(
    private val contactsDataSource: ContactsDataSource,
) : ContactGateway {
    override suspend fun saveContact(contact: Contact): Long {
        return contactsDataSource.saveContact(contact.toContactEntity())
    }

    override fun getContact(contactId: Long): Flow<Contact?> {
        return contactsDataSource.getContact(contactId).map { it?.toContact() }
    }

    override fun getContacts(): Flow<List<Contact>> {
        return contactsDataSource.getAllContacts().map { it.toContacts() }
    }
}
