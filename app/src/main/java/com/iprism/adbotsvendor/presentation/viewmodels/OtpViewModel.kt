package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.LoginApiResponse
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.repositories.AuthRepository
import com.iprism.adbotsvendor.presentation.viewmodels.LoginViewModel.LoginEvent
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableStateFlow<UiState<LoginApiResponse>>(UiState.Idle)
    val loginResponse: StateFlow<UiState<LoginApiResponse>> = _loginResponse

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object NavigateToRegister : UiEvent()
        object NavigateToHome : UiEvent()
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResponse.value = UiState.Loading
            try {
                Log.d("requestLoading", request.toString())
                val response = repository.login(request)
                if (response.status) {
                    _loginResponse.value = UiState.Success(response)
                    if (response.response.userDetails.registartionStatus.equals("yes", true)) {
                        _eventFlow.emit(UiEvent.NavigateToHome)
                    } else {
                        _eventFlow.emit(UiEvent.NavigateToRegister)
                    }
                } else {
                    _loginResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

fun isValidMobile(mobile : String) {
    if (mobile.length != 10) {

    }
}