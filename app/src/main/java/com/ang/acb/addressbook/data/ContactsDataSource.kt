package com.ang.acb.addressbook.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactsDataSource @Inject constructor(
    private val contactDao: ContactDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun saveContact(contact: ContactEntity): Long {
        return withContext(ioDispatcher) {
            contactDao.insert(contact)
        }
    }

    fun getContact(contactId: Long): Flow<ContactEntity?> {
        return contactDao.getContact(contactId)
    }

    fun getAllContacts(): Flow<List<ContactEntity>> {
        return contactDao.getAllContacts()
    }
}
