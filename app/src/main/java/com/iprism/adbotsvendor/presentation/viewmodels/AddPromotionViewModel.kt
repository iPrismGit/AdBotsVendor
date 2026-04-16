package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.User
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsApiResponse
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsRequest
import com.iprism.adbotsvendor.data.repositories.AuthRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class PromotionFormState(
    val name: String = "",
    val businessName: String = "",
    val mobile: String = "",
    val cityId: String = "",
    val cityName: String = "",
    val areaId: String = "",
    val areaName: String = "",
    val categoryId: String = "",
    val categoryName: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val screenCount: Int = 1
)

@HiltViewModel
class AddPromotionViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _dropDownsResponse = MutableStateFlow<UiState<DropDownsApiResponse>>(UiState.Idle)
    val dropDownsResponse: StateFlow<UiState<DropDownsApiResponse>> = _dropDownsResponse.asStateFlow()

    private val _areasResponse = MutableStateFlow<UiState<DropDownsApiResponse>>(UiState.Idle)
    val areasResponse: StateFlow<UiState<DropDownsApiResponse>> = _areasResponse.asStateFlow()

    private val _formState = MutableStateFlow(PromotionFormState())
    val formState: StateFlow<PromotionFormState> = _formState.asStateFlow()

    private val _validationEvent = MutableSharedFlow<Boolean>()
    val validationEvent = _validationEvent.asSharedFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private var userCache: User? = null

    private suspend fun getUser(): User {
        return userCache ?: dataStoreManager.userDetails.first().also {
            userCache = it
        }
    }

    fun updateName(name: String) = _formState.update { it.copy(name = name) }
    fun updateBusinessName(businessName: String) = _formState.update { it.copy(businessName = businessName) }
    fun updateMobile(mobile: String) = _formState.update { it.copy(mobile = mobile) }
    
    fun updateCity(id: String, name: String) {
        _formState.update { it.copy(cityId = id, cityName = name, areaId = "", areaName = "") }
        fetchAreas(id)
    }
    
    fun updateArea(id: String, name: String) = _formState.update { it.copy(areaId = id, areaName = name) }
    fun updateCategory(id: String, name: String) = _formState.update { it.copy(categoryId = id, categoryName = name) }

    fun validateBusinessDetails() {
        val state = _formState.value
        val error = when {
            state.name.isBlank() -> "Please enter your name"
            state.businessName.isBlank() -> "Please enter business name"
            state.mobile.length != 10 -> "Please enter a valid 10-digit mobile number"
            state.cityId.isBlank() -> "Please select a city"
            state.areaId.isBlank() -> "Please select an area"
            state.categoryId.isBlank() -> "Please select a business category"
            else -> null
        }

        viewModelScope.launch {
            if (error != null) {
                _toastMessage.emit(error)
            } else {
                _validationEvent.emit(true)
            }
        }
    }

    init {
        fetchDropDowns()
    }

    fun setDates(start: String, end: String) {
        _formState.update { it.copy(startDate = start, endDate = end) }
    }

    fun setScreenCount(count: Int) {
        _formState.update { it.copy(screenCount = count) }
        logAllData()
    }

    private fun logAllData() {
        val state = _formState.value
        Log.d("AddPromotion", """
            Form Data:
            Your Name: ${state.name}
            Business Name: ${state.businessName}
            Mobile: ${state.mobile}
            City: ${state.cityName} (ID: ${state.cityId})
            Area: ${state.areaName} (ID: ${state.areaId})
            Category: ${state.categoryName} (ID: ${state.categoryId})
            Start Date: ${state.startDate}
            End Date: ${state.endDate}
            Screen Count: ${state.screenCount}
        """.trimIndent())
    }

    fun fetchDropDowns() {
        viewModelScope.launch {
            _dropDownsResponse.value = UiState.Loading
            try {
                val user = getUser()
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

    private fun fetchAreas(cityId: String) {
        viewModelScope.launch {
            _areasResponse.value = UiState.Loading
            try {
                val user = getUser()
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
