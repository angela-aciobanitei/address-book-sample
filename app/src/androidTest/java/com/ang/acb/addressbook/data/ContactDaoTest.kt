package com.ang.acb.addressbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@SmallTest
@RunWith(AndroidJUnit4::class)
class ContactDaoTest {
    private lateinit var database: ContactDatabase
    private lateinit var contactDao: ContactDao

    private val contactA = ContactEntity(1L, "A", "A", "a@email.com", "12345", "YO 11 WE")
    private val contactB = ContactEntity(2L, "B", "B", "b@email.com", "12345", "YO 11 WE")
    private val contactC = ContactEntity(3L, "C", "C", "c@email.com", "12345", "YO 11 WE")

    // Swaps the background executor used by the Architecture Components
    // with a different one which executes each task synchronously.
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        runBlocking {
            // Create an in-memory version of the database
            val context = InstrumentationRegistry.getInstrumentation().targetContext
            database = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java).build()
            contactDao = database.contactDao

            // Insert contacts in non-alphabetical order to test that results are sorted by last name
            contactDao.insert(contactB)
            contactDao.insert(contactA)
            contactDao.insert(contactC)
        }
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetContacts() {
        runBlocking {
            val contacts = contactDao.getAllContacts().first()
            assertThat(contacts.size, `is`(3))

            // Ensure list is sorted by last name
            assertThat(contacts[0], `is`(contactA))
            assertThat(contacts[1], `is`(contactB))
            assertThat(contacts[2], `is`(contactC))
        }
    }

    @Test
    fun testGetContact() {
        runBlocking {
            assertThat(contactDao.getContact(contactA.id).first(), notNullValue())
            assertThat(contactDao.getContact(contactA.id).first(), `is`(contactA))
        }
    }
}
