package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.LoginApiResponse
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.otp.OtpRequest
import com.iprism.adbotsvendor.data.models.otp.ResendOtpApiResponse
import com.iprism.adbotsvendor.data.repositories.AuthRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _loginResponse = MutableStateFlow<UiState<LoginApiResponse>>(UiState.Idle)
    val loginResponse: StateFlow<UiState<LoginApiResponse>> = _loginResponse

    private val _resendOtpResponse = MutableStateFlow<UiState<ResendOtpApiResponse>>(UiState.Idle)
    val resendOtpResponse: StateFlow<UiState<ResendOtpApiResponse>> = _resendOtpResponse

    private val _eventFlow = Channel<UiEvent>()
    val eventFlow = _eventFlow.receiveAsFlow()

    sealed class UiEvent {
        class NavigateToRegister(val mobile: String) : UiEvent()
        object NavigateToHome : UiEvent()
        data class ShowToast(val message: String) : UiEvent()
    }

    fun login(request: LoginRequest) {
        viewModelScope.launch {
            _loginResponse.value = UiState.Loading
            try {
                Log.d("requestLoading", request.toString())
                val response = repository.login(request)
                if (response.status) {
                    _loginResponse.value = UiState.Success(response)
                    val user = response.response.userDetails
                    Log.d("requestLoading", response.response.userDetails.toString())
                    dataStoreManager.saveUser(
                        userId = user.id,
                        userName = user.name,
                        token = user.authToken
                    )
                    if (user.registartionStatus.equals("yes", true)) {
                        dataStoreManager.loginUser()
                        _eventFlow.send(UiEvent.NavigateToHome)
                    } else {
                        _eventFlow.send(UiEvent.NavigateToRegister(request.mobile))
                    }
                } else {
                    _loginResponse.value = UiState.Error(response.message)
                    _eventFlow.send(UiEvent.ShowToast(response.message))
                }
            } catch (e: Exception) {
                _loginResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
                _eventFlow.send(UiEvent.ShowToast(e.localizedMessage ?: "Unknown error"))
            }
        }
    }

    fun resendOtp(mobile: String) {
        viewModelScope.launch {
            _resendOtpResponse.value = UiState.Loading
            try {
                val request = OtpRequest(mobile)
                val response = repository.resendOtp(request)
                if (response.status) {
                    _resendOtpResponse.value = UiState.Success(response)
                    _eventFlow.send(UiEvent.ShowToast("${response.message} OTP: ${response.response.otp}"))
                } else {
                    _resendOtpResponse.value = UiState.Error(response.message)
                    _eventFlow.send(UiEvent.ShowToast(response.message))
                }
            } catch (e: Exception) {
                _resendOtpResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
                _eventFlow.send(UiEvent.ShowToast(e.localizedMessage ?: "Unknown error"))
            }
        }
    }
}
