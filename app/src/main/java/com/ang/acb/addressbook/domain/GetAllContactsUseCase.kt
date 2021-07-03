package com.ang.acb.addressbook.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(
    private val contactsGateway: ContactsGateway,
) {
    operator fun invoke(): Flow<List<Contact>> {
        return contactsGateway.getContacts()
    }
}
