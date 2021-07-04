package com.ang.acb.addressbook.di

import com.ang.acb.addressbook.data.ContactsRepository
import com.ang.acb.addressbook.domain.ContactsGateway
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideContactGateway(
        contactsRepository: ContactsRepository
    ): ContactsGateway = contactsRepository

    @Singleton
    @Provides
    fun provideIoDispatcher() = Dispatchers.IO
}
