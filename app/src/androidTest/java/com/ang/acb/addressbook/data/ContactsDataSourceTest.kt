package com.ang.acb.addressbook.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.ang.acb.addressbook.MainCoroutineRule
import com.ang.acb.addressbook.data.contacts.ContactDao
import com.ang.acb.addressbook.data.contacts.ContactDatabase
import com.ang.acb.addressbook.data.contacts.ContactsDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ContactsDataSourceTest {

    private lateinit var dataSource: ContactsDataSource
    private lateinit var database: ContactDatabase
    private lateinit var contactDao: ContactDao

    // Swaps the background executor used by the Architecture Components
    // with a different one which executes each task synchronously.
    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher to a TestCoroutineDispatcher
    // with a TestCoroutineScope.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun initDb() {
        // Create an in-memory version of the database
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        contactDao = database.contactDao

        dataSource = ContactsDataSource(
            contactDao,
            Dispatchers.Main
        )
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertContactAndGetById() {
        mainCoroutineRule.runBlockingTest {
            // Given a new contact that is saved
            val firstName = "Jane"
            val lastName = "Doe"
            val email = "janedoe@email.com"
            val phoneNumber = "12345"
            val address = "YO11 1AL"
            val testId = dataSource.saveContact(firstName, lastName, email, phoneNumber, address)

            // When the contact is retrieved
            val loaded = dataSource.getContact(testId).first()

            // Then the loaded data contains the expected values
            assertThat(loaded, notNullValue())
            assertThat(loaded?.id, `is`(testId))
            assertThat(loaded?.firstName, `is`(firstName))
            assertThat(loaded?.lastName, `is`(lastName))
            assertThat(loaded?.email, `is`(email))
            assertThat(loaded?.phoneNumber, `is`(phoneNumber))
            assertThat(loaded?.address, `is`(address))
        }
    }

    @Test
    fun insertContactsAndLoadAll() {
        mainCoroutineRule.runBlockingTest {
            // Given 3 contacts that are saved
            val testId1 = dataSource.saveContact(
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@email.com",
                phoneNumber = "07470484432",
                address = "YO11 1AB",
            )

            val testId2 = dataSource.saveContact(
                firstName = "Jack",
                lastName = "London",
                email = "jack.london@email.com",
                phoneNumber = "07470484433",
                address = "YO11 1AC",
            )

            val testId3 = dataSource.saveContact(
                firstName = "Dave",
                lastName = "Miles",
                email = "jack.london@email.com",
                phoneNumber = "07470484433",
                address = "YO11 1AC",
            )

            // When loading all the contacts
            val loaded = dataSource.getAllContacts().first()

            // Then the loaded list is sorted by last name
            assertThat(loaded, notNullValue())
            assertThat(loaded.size, `is`(3))
            assertThat(loaded[0].id, `is`(testId1))
            assertThat(loaded[1].id, `is`(testId2))
            assertThat(loaded[2].id, `is`(testId3))
        }
    }
}
