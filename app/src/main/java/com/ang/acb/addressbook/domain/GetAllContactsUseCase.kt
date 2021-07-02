package com.ang.acb.addressbook.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllContactsUseCase @Inject constructor(
    private val contactGateway: ContactGateway,
) {
    operator fun invoke(): Flow<List<Contact>> {
        return contactGateway.getContacts()
    }
}
