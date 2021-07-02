package com.ang.acb.addressbook.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetContactUseCase @Inject constructor(
    private val contactGateway: ContactGateway,
) {
    operator fun invoke(contactId: Long): Flow<Contact?> {
        return contactGateway.getContact(contactId)
    }
}
