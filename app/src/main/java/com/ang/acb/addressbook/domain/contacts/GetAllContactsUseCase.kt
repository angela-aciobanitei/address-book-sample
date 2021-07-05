package com.ang.acb.addressbook.domain.contacts

import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.ContactsGateway
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(
    private val contactsGateway: ContactsGateway,
) {
    operator fun invoke(): Flow<List<Contact>> {
        return contactsGateway.getContacts()
    }
}
