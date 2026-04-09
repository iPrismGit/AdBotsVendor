package com.iprism.adbotsvendor.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.LoginApiResponse
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.repositories.AuthRepository
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _loginResponse = MutableStateFlow<UiState<LoginApiResponse>>(UiState.Idle)
    val loginResponse: StateFlow<UiState<LoginApiResponse>> = _loginResponse

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    sealed class LoginEvent {
        object NavigateHome : LoginEvent()
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResponse.value = UiState.Loading
            try {
                val response = repository.login(request)
                if (response.status) {
                    _loginResponse.value = UiState.Success(response)
                    _event.emit(LoginEvent.NavigateHome)
                } else {
                    _loginResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _loginResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}