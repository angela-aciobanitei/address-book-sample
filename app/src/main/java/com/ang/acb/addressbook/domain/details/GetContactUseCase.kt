package com.ang.acb.addressbook.domain.details

import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.ContactsGateway
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactUseCase @Inject constructor(
    private val contactsGateway: ContactsGateway,
) {
    operator fun invoke(contactId: Long): Flow<Contact?> {
        return contactsGateway.getContact(contactId)
    }
}
