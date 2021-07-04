package com.ang.acb.addressbook.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ContactEntity::class], version = 3, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDao: ContactDao
}
