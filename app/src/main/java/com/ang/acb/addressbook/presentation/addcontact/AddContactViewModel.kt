package com.ang.acb.addressbook.presentation.addcontact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ang.acb.addressbook.R
import com.ang.acb.addressbook.domain.SaveContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AddContactViewModel @Inject constructor(
    private val saveContactUseCase: SaveContactUseCase,
) : ViewModel() {

    private val _message: MutableLiveData<Int> = MutableLiveData()
    val message: LiveData<Int> = _message

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun saveContact() {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val newContactId = saveContactUseCase(
                    "Jane",
                    "Doe",
                    "jane.doe@email.com", // todo verify email with regex
                    "+44 0123456789",
                    "YO11 1AA"
                )
                Timber.d("asd inserted contact with ID=$newContactId")
            } catch (e: Exception) {
                Timber.e(e)
                _message.postValue(R.string.save_contact_details_error_message)
            }
            _loading.postValue(false)
        }
    }
}
