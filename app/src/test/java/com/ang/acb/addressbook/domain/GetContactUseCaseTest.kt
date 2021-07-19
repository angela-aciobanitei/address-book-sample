package com.ang.acb.addressbook.domain

import com.ang.acb.addressbook.FakeContactsRepository
import com.ang.acb.addressbook.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetContactUseCaseTest {
    // Subject under test
    private lateinit var useCase: GetContactUseCase

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeContactsRepository

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        fakeRepository = FakeContactsRepository()
        useCase = GetContactUseCase(fakeRepository)
    }

    @Test
    fun testGetContact() {
        mainCoroutineRule.runBlockingTest {
            // Given a new contact that is saved
            val firstName = "Joe"
            val lastName = "Smith"
            val email = "joe.smith@email.com"
            val phoneNumber = "12345"
            val address = "YO11 1AL"
            val testId =
                fakeRepository.saveContact(firstName, lastName, email, phoneNumber, address)

            // When the contact is retrieved
            val loaded = useCase(testId).first()

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
}
