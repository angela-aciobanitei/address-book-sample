package com.ang.acb.addressbook.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ang.acb.addressbook.FakeContactsRepository
import com.ang.acb.addressbook.MainCoroutineRule
import com.ang.acb.addressbook.details.ContactDetailsViewModel
import com.ang.acb.addressbook.domain.GetContactUseCase
import com.ang.acb.addressbook.utils.getOrAwaitValue
import com.ang.acb.addressbook.utils.observeForTesting
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ContactDetailsViewModelTest {

    // Subject under test
    private lateinit var viewModel: ContactDetailsViewModel

    // Use a fake repository to be injected into the use case
    private lateinit var fakeRepository: FakeContactsRepository

    // Sets the main coroutines dispatcher for unit testing
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        fakeRepository = FakeContactsRepository()
        viewModel = ContactDetailsViewModel(GetContactUseCase(fakeRepository))
    }

    @Test
    fun loadContactDetails_loadingTogglesAndDataIsLoaded() {
        val id = runBlocking {
            fakeRepository.saveContact(
                firstName = "Brian",
                lastName = "Adams",
                email = "brian.adams@email.com",
                phoneNumber = "07470484432",
                address = "YO11 1AB",
            )
        }

        // Pause dispatcher so we can verify initial values
        mainCoroutineRule.pauseDispatcher()

        // Trigger loading of contact
        viewModel.getContact(id)
        // Observe the contacts to keep LiveData emitting
        viewModel.contact.observeForTesting {
            // Then progress indicator is shown
            assertThat(viewModel.loading.getOrAwaitValue(), `is`(true))

            // Execute pending coroutines actions
            mainCoroutineRule.resumeDispatcher()

            // Then progress indicator is hidden
            assertThat(viewModel.loading.getOrAwaitValue(), `is`(false))

            // And data correctly loaded
            assertThat(viewModel.contact.getOrAwaitValue(), notNullValue())
            assertThat(viewModel.contact.getOrAwaitValue().id, `is`(id))
        }
    }
}
