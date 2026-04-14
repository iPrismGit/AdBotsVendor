package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.register.RegisterApiResponse
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
import com.iprism.adbotsvendor.data.repositories.AuthRepository
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
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _registerResponse = MutableStateFlow<UiState<RegisterApiResponse>>(UiState.Idle)
    val registerResponse: StateFlow<UiState<RegisterApiResponse>> = _registerResponse

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        data class ShowToast(val message: String) : UiEvent()
        object NavigateToHome : UiEvent()
    }

    fun registerUser(
        area: String,
        city: String,
        businessName: String,
        name: String,
        vendorCategory: String
    ) {
        viewModelScope.launch {
            when {
                name.isEmpty() -> {
                    _eventFlow.emit(UiEvent.ShowToast("Please Enter Your Name"))
                    return@launch
                }

                businessName.isEmpty() -> {
                    _eventFlow.emit(UiEvent.ShowToast("Please Enter Business Name"))
                    return@launch
                }

                area.isEmpty() -> {
                    _eventFlow.emit(UiEvent.ShowToast("Please Select Area"))
                    return@launch
                }

                city.isEmpty() -> {
                    _eventFlow.emit(UiEvent.ShowToast("Please Select City"))
                    return@launch
                }

                vendorCategory.isEmpty() -> {
                    _eventFlow.emit(UiEvent.ShowToast("Please Select Business Category"))
                    return@launch
                }
            }

            try {
                val user = dataStoreManager.userDetails.first()
                val request = RegisterRequest(
                    area = area,
                    userId = user.userId ?: "",
                    bussinessName = businessName,
                    city = city,
                    name = name,
                    vendorCategory = vendorCategory
                )
                _registerResponse.value = UiState.Loading
                Log.d("requestLoading", request.toString())
                val response = repository.register(request)
                if (response.status) {
                    _registerResponse.value = UiState.Success(response)
                    _eventFlow.emit(UiEvent.ShowToast("Submitted"))
                } else {
                    _registerResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _registerResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}