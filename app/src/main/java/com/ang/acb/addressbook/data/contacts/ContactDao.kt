package com.ang.acb.addressbook.data.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contact: ContactEntity): Long

    // Note: when the return type is Flow<T>, querying an empty table throws a NPE.
    // When the return type is Flow<T?>, querying an empty table emits a null value.
    @Query("SELECT * FROM contact WHERE contact.id = :contactId")
    fun getContact(contactId: Long): Flow<ContactEntity?>

    // Note: when the return type is Flow<List<T>>, querying an empty table emits an empty list.
    @Query("SELECT * FROM contact ORDER BY last_name")
    fun getAllContacts(): Flow<List<ContactEntity>>
}
