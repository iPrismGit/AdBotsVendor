package com.iprism.adbotsvendor.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.contactus.ContactUsApiResponse
import com.iprism.adbotsvendor.data.models.contactus.ContactUsRequest
import com.iprism.adbotsvendor.data.repositories.SettingsRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val repository: SettingsRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _contactUsResponse = MutableStateFlow<UiState<ContactUsApiResponse>>(UiState.Idle)
    val contactUsResponse: StateFlow<UiState<ContactUsApiResponse>> = _contactUsResponse

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object Success : UiEvent()
        data class MakeCall(val phoneNumber: String) : UiEvent()
    }

    init {
        fetchContactUs()
    }

    fun fetchContactUs() {
        viewModelScope.launch {
            _contactUsResponse.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = ContactUsRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    name = "",
                    email = "",
                    mobile = 0,
                    message = "",
                    viewType = "view",
                    authToken = user.token ?: ""
                )
                val response = repository.contactUs(request)
                if (response.status) {
                    _contactUsResponse.value = UiState.Success(response)
                } else {
                    _contactUsResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _contactUsResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun submitContactUs(name: String, email: String, mobile: String, message: String) {
        if (name.isBlank()) {
            viewModelScope.launch { _eventFlow.emit(UiEvent.ShowToast("Please enter your name")) }
            return
        }
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            viewModelScope.launch { _eventFlow.emit(UiEvent.ShowToast("Please enter a valid email")) }
            return
        }
        if (mobile.length != 10) {
            viewModelScope.launch { _eventFlow.emit(UiEvent.ShowToast("Please enter a valid 10-digit mobile number")) }
            return
        }
        if (message.isBlank()) {
            viewModelScope.launch { _eventFlow.emit(UiEvent.ShowToast("Please enter your message")) }
            return
        }

        viewModelScope.launch {
            _contactUsResponse.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = ContactUsRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    name = name,
                    email = email,
                    mobile = mobile.toLongOrNull() ?: 0,
                    message = message,
                    viewType = "update",
                    authToken = user.token ?: ""
                )
                val response = repository.contactUs(request)
                if (response.status) {
                    _contactUsResponse.value = UiState.Success(response)
                    _eventFlow.emit(UiEvent.ShowToast(response.message))
                    _eventFlow.emit(UiEvent.Success)
                } else {
                    _contactUsResponse.value = UiState.Error(response.message)
                    _eventFlow.emit(UiEvent.ShowToast(response.message))
                }
            } catch (e: Exception) {
                _contactUsResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
                _eventFlow.emit(UiEvent.ShowToast(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    fun onCallClicked() {
        val state = _contactUsResponse.value
        if (state is UiState.Success) {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.MakeCall(state.data.response.mobile.toString()))
            }
        } else {
            viewModelScope.launch {
                _eventFlow.emit(UiEvent.ShowToast("Contact number not available"))
            }
        }
    }
}
