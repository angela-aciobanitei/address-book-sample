package com.ang.acb.addressbook.presentation.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.domain.create.CreateContactUseCase
import com.ang.acb.addressbook.presentation.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateContactViewModel @Inject constructor(
    private val createContactUseCase: CreateContactUseCase,
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>> = _message

    private val _navigation = MutableLiveData<Event<Navigation>>()
    val navigation: LiveData<Event<Navigation>> = _navigation

    fun saveContact(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        address: String,
    ) {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val newContactId = createContactUseCase(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phoneNumber = phoneNumber,
                    address = address
                )
                if (newContactId != -1L) {
                    _navigation.postValue(Event(Navigation.Up))
                } else {
                    _message.postValue(Event(R.string.save_contact_details_error_message))
                }
            } catch (e: Exception) {
                Timber.e(e)
                _message.postValue(Event(R.string.save_contact_details_error_message))
            }
            _loading.postValue(false)
        }
    }

    sealed class Navigation {
        object Up : Navigation()
    }
}
