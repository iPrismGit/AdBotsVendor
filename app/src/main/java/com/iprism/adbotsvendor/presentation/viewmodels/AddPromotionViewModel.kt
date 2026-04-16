package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsApiResponse
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsRequest
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
class AddPromotionViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _dropDownsResponse = MutableStateFlow<UiState<DropDownsApiResponse>>(UiState.Idle)
    val dropDownsResponse: StateFlow<UiState<DropDownsApiResponse>> = _dropDownsResponse

    private val _areasResponse = MutableStateFlow<UiState<DropDownsApiResponse>>(UiState.Idle)
    val areasResponse: StateFlow<UiState<DropDownsApiResponse>> = _areasResponse

    fun fetchDropDowns() {
        viewModelScope.launch {
            _dropDownsResponse.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = DropDownsRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    viewType = "view",
                    authToken = user.token ?: "",
                    cityId = ""
                )
                val response = repository.fetchDropDowns(request)
                if (response.status) {
                    _dropDownsResponse.value = UiState.Success(response)
                } else {
                    _dropDownsResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _dropDownsResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun fetchAreas(cityId: String) {
        viewModelScope.launch {
            _areasResponse.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = DropDownsRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    viewType = "view_area",
                    authToken = user.token ?: "",
                    cityId = cityId
                )
                val response = repository.fetchDropDowns(request)
                if (response.status) {
                    _areasResponse.value = UiState.Success(response)
                } else {
                    _areasResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _areasResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}