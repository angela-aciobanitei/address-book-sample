package com.ang.acb.addressbook.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ang.acb.addressbook.FakeContactsRepository
import com.ang.acb.addressbook.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveContactUseCaseTest {
    // Subject under test
    private lateinit var useCase: SaveContactUseCase

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeContactsRepository

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeContactsRepository()
        useCase = SaveContactUseCase(fakeRepository)
    }

    @Test
    fun testSaveNewContact() {
        mainCoroutineRule.runBlockingTest {
            // Given a new contact that is saved
            val firstName = "Jane"
            val lastName = "Doe"
            val email = "janedoe@email.com"
            val phoneNumber = "12345"
            val address = "YO11 1AL"
            val testId = useCase(firstName, lastName, email, phoneNumber, address)

            // When the contact is retrieved
            val loaded = fakeRepository.contacts.values.first()

            // Then the loaded data contains the expected values
            assertThat(loaded, notNullValue())
            assertThat(loaded.id, `is`(testId))
            assertThat(loaded.firstName, `is`(firstName))
            assertThat(loaded.lastName, `is`(lastName))
            assertThat(loaded.email, `is`(email))
            assertThat(loaded.phoneNumber, `is`(phoneNumber))
            assertThat(loaded.address, `is`(address))
        }
    }
}
