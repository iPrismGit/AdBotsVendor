package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.profile.ProfileApiResponse
import com.iprism.adbotsvendor.data.models.profile.ProfileRequest
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
class ProfileViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _profileState = MutableStateFlow<UiState<ProfileApiResponse>>(UiState.Idle)
    val profileState: StateFlow<UiState<ProfileApiResponse>> = _profileState

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    sealed class UiEvent {
        object Logout : UiEvent()
        data class Error(val message: String) : UiEvent()
    }

    fun fetchProfile() {
        viewModelScope.launch {
            _profileState.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = ProfileRequest(
                    userId = user.userId ?: "",
                    viewType = "view",
                    area = "", city = "", bussinessName = "", name = "", vendorCategory = ""
                )
                Log.d("requestLoading", request.toString())
                val response = repository.profile(request)
                if (response.status) {
                    _profileState.value = UiState.Success(response)
                } else {
                    _profileState.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _profileState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearData()
            _eventFlow.emit(UiEvent.Logout)
        }
    }
}
