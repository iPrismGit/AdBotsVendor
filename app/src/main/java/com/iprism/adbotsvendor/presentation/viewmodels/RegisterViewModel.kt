package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.register.RegisterApiResponse
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
import com.iprism.adbotsvendor.data.repositories.AuthRepository
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {

    private val _registerResponse = MutableStateFlow<UiState<RegisterApiResponse>>(UiState.Idle)
    val registerResponse: StateFlow<UiState<RegisterApiResponse>> = _registerResponse

    fun register(request: RegisterRequest) {
        viewModelScope.launch {
            _registerResponse.value = UiState.Loading
            try {
                Log.d("requestLoading", request.toString())
                val response = repository.register(request)
                if (response.status) {
                    _registerResponse.value = UiState.Success(response)
                } else {
                    _registerResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _registerResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}