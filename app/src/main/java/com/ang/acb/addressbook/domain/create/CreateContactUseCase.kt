package com.ang.acb.addressbook.domain.create

import com.ang.acb.addressbook.domain.ContactsGateway
import javax.inject.Inject

class CreateContactUseCase @Inject constructor(
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
