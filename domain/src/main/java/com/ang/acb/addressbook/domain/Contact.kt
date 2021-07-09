package com.ang.acb.addressbook.domain

data class Contact(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
)
