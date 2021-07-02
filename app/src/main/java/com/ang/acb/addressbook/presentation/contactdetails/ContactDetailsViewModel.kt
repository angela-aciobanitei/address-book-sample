package com.ang.acb.addressbook.presentation.contactdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.GetContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val getContactUseCase: GetContactUseCase,
) : ViewModel() {
    private val _contact: MutableLiveData<Contact> = MutableLiveData()
    val contact: LiveData<Contact> = _contact

    private val _message: MutableLiveData<Int> = MutableLiveData()
    val message: LiveData<Int> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getContact(contactId: Long) {
        viewModelScope.launch {
            _loading.postValue(true)
            getContactUseCase(contactId)
                .catch {
                    Timber.e(it)
                    _loading.postValue(false)
                    _message.postValue(R.string.get_contact_details_error_message)
                }
                .collect {
                    _contact.postValue(it)
                    _loading.postValue(false)
                    Timber.d("asd contact: $it")
                }
        }
    }
}
