package com.ang.acb.addressbook.di

import android.content.Context
import androidx.room.Room
import com.ang.acb.addressbook.data.ContactDao
import com.ang.acb.addressbook.data.ContactDatabase
import com.ang.acb.addressbook.data.ContactsRepository
import com.ang.acb.addressbook.domain.ContactsGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContactGateway(
        contactsRepository: ContactsRepository
    ): ContactsGateway = contactsRepository

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

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
