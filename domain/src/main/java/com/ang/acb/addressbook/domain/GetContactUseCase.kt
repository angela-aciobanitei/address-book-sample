package com.ang.acb.addressbook.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactUseCase @Inject constructor(
    private val contactsGateway: ContactsGateway,
) {
    operator fun invoke(contactId: Long): Flow<Contact?> {
        return contactsGateway.getContact(contactId)
    }
}
