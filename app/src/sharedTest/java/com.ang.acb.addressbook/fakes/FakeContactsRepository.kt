package com.ang.acb.addressbook.fakes

import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.ContactsGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class FakeContactsRepository : ContactsGateway {

    private var contacts: LinkedHashMap<Long, Contact> = LinkedHashMap()
    private var initialId = 0L

    override suspend fun saveContact(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        address: String
    ): Long {
        val contact = Contact(
            id = ++initialId,
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            address = address
        )
        contacts[contact.id] = contact
        return contacts.filterValues { it == contact }.keys.first()
    }

    // See: https://developer.android.com/kotlin/flow/test
    override fun getContact(contactId: Long): Flow<Contact?> = flow {
        emit(contacts[contactId])
    }

    override fun getContacts(): Flow<List<Contact>> = flow {
        emit(contacts.values.toList())
    }
}
