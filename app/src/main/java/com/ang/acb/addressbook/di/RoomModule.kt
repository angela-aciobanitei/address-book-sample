package com.ang.acb.addressbook.di

import android.content.Context
import androidx.room.Room
import com.ang.acb.addressbook.data.ContactDao
import com.ang.acb.addressbook.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): ContactDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ContactDatabase::class.java,
            "contact.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDao(database: ContactDatabase): ContactDao {
        return database.contactDao
    }
}
