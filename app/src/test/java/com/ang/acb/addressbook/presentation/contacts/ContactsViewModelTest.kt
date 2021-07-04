package com.ang.acb.addressbook.presentation.contacts

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ang.acb.addressbook.domain.GetAllContactsUseCase
import com.ang.acb.addressbook.fakes.FakeContactsRepository
import com.ang.acb.addressbook.utils.MainCoroutineRule
import com.ang.acb.addressbook.utils.getOrAwaitValue
import com.ang.acb.addressbook.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactsViewModelTest {

    // Subject under test
    private lateinit var contactsViewModel: ContactsViewModel

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeContactsRepository

    // Set the main coroutines dispatcher for unit testing.
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeRepository = FakeContactsRepository()
        runBlocking {
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
        }

        contactsViewModel = ContactsViewModel(GetAllContactsUseCase(fakeRepository))
    }

    @Test
    fun loadAllContacts_loadingTogglesAndDataIsLoaded() {
        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // Trigger loading of contacts
        contactsViewModel.loadContacts()
        // Observe the contacts to keep LiveData emitting
        contactsViewModel.contacts.observeForTesting {
            // Then progress indicator is shown
            assertThat(contactsViewModel.loading.getOrAwaitValue(), `is`(true))

            // Execute pending coroutines actions
            mainCoroutineRule.resumeDispatcher()

            // Then progress indicator is hidden
            assertThat(contactsViewModel.loading.getOrAwaitValue(), `is`(false))

            // And data correctly loaded
            assertThat(contactsViewModel.contacts.getOrAwaitValue().size, `is`(3))
        }
    }
}
