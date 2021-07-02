package com.ang.acb.addressbook.domain

import javax.inject.Inject

class SaveContactUseCase @Inject constructor(
    private val contactGateway: ContactGateway,
) {
    suspend operator fun invoke(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        address: String,
    ): Long {
        return contactGateway.saveContact(
            firstName = firstName,
            lastName = lastName,
            email = email,
            phoneNumber = phoneNumber,
            address = address
        )
    }
}
