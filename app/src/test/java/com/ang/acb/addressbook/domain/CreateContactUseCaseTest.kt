package com.ang.acb.addressbook.domain

import com.ang.acb.addressbook.FakeContactsRepository
import com.ang.acb.addressbook.MainCoroutineRule
import com.ang.acb.addressbook.domain.create.CreateContactUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CreateContactUseCaseTest {
    // Subject under test
    private lateinit var useCase: CreateContactUseCase

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeContactsRepository

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeContactsRepository()
        useCase = CreateContactUseCase(fakeRepository)
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
            val loaded: Contact = fakeRepository.contacts.values.first()

            // Then the loaded data contains the expected values
            assertThat(
                loaded, `is`(
                    Contact(
                        testId, firstName, lastName, email, phoneNumber, address
                    )
                )
            )
        }
    }
}
