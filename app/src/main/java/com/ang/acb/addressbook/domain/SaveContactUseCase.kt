package com.ang.acb.addressbook.domain

import javax.inject.Inject

class SaveContactUseCase @Inject constructor(
    private val contactsGateway: ContactsGateway,
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        address: String,
    ): Long {
        return contactsGateway.saveContact(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            address = address,
        )
    }
}
