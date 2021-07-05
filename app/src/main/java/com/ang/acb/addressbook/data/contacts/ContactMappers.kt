package com.ang.acb.addressbook.data.contacts

import com.ang.acb.addressbook.domain.Contact

fun ContactEntity.toContact() = Contact(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phoneNumber = phoneNumber,
    address = address
)

fun List<ContactEntity>.toContacts() = this.map { it.toContact() }

