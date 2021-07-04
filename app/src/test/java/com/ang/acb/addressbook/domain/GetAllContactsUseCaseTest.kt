package com.ang.acb.addressbook.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ang.acb.addressbook.FakeContactsRepository
import com.ang.acb.addressbook.utils.TestCoroutineRule
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
class GetAllContactsUseCaseTest {
    // Subject under test
    private lateinit var useCase: GetAllContactsUseCase

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeContactsRepository

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        fakeRepository = FakeContactsRepository()
        useCase = GetAllContactsUseCase(fakeRepository)
    }

    @Test
    fun testGetContact() {
        testCoroutineRule.runBlockingTest {
            // Given 3 contacts that are saved
            fakeRepository.saveContact(
                firstName = "Jane",
                lastName = "Doe",
                email = "jane.doe@email.com",
                phoneNumber = "07470484432",
                address = "YO11 1AB",
            )

            fakeRepository.saveContact(
                firstName = "Jack",
                lastName = "London",
                email = "jack.london@email.com",
                phoneNumber = "07470484433",
                address = "YO11 1AC",
            )

            fakeRepository.saveContact(
                firstName = "Dave",
                lastName = "Miles",
                email = "jack.london@email.com",
                phoneNumber = "07470484433",
                address = "YO11 1AC",
            )

            // When loading all the contacts
            val loaded = useCase().first()

            // Then the loaded data contains the expected values
            assertThat(loaded, notNullValue())
            assertThat(loaded.size, `is`(3))
        }
    }
}
