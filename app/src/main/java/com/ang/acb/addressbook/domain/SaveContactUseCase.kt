package com.ang.acb.addressbook.domain

import javax.inject.Inject

class SaveContactUseCase @Inject constructor(
    private val contactGateway: ContactGateway,
) {
    suspend operator fun invoke(contact: Contact): Long {
        return contactGateway.saveContact(contact)
    }
}
