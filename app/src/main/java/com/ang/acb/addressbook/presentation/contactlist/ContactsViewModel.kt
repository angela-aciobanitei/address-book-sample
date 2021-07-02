package com.ang.acb.addressbook.presentation.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.domain.Contact
import com.ang.acb.addressbook.domain.GetAllContactsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val getAllContactsUseCase: GetAllContactsUseCase,
) : ViewModel() {

    private val _contacts: MutableLiveData<List<Contact>> = MutableLiveData()
    val contacts: LiveData<List<Contact>> = _contacts

    private val _message: MutableLiveData<Int> = MutableLiveData()
    val message: LiveData<Int> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        viewModelScope.launch {
            _loading.postValue(true)
            getAllContactsUseCase()
                .catch {
                    Timber.e(it)
                    _loading.postValue(false)
                    _message.postValue(R.string.get_contacts_error_message)
                }
                .collect {
                    _contacts.postValue(it)
                    _loading.postValue(false)
                    Timber.d("asd contacts: ${it.size}")
                }
        }
    }
}
