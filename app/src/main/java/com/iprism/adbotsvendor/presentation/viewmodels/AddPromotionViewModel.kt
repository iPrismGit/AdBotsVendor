package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _validationEvent = MutableSharedFlow<Boolean>()
    val validationEvent = _validationEvent.asSharedFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    // Form data
    var name: String = ""
    var businessName: String = ""
    var mobile: String = ""
    var city: String = ""
    var area: String = ""
    var category: String = ""
    var startDate: String = ""
    var endDate: String = ""
    var screenCount1: Int = 1

    fun validateBusinessDetails(
        name: String,
        businessName: String,
        mobile: String,
        city: String?,
        area: String?,
        category: String?
    ) {
        val error = when {
            name.isBlank() -> "Please enter your name"
            businessName.isBlank() -> "Please enter business name"
            mobile.length != 10 -> "Please enter a valid 10-digit mobile number"
            city == null -> "Please select a city"
            area == null -> "Please select an area"
            category == null -> "Please select a business category"
            else -> null
        }

        viewModelScope.launch {
            if (error != null) {
                _toastMessage.emit(error)
            } else {
                this@AddPromotionViewModel.name = name
                this@AddPromotionViewModel.businessName = businessName
                this@AddPromotionViewModel.mobile = mobile
                this@AddPromotionViewModel.city = city ?: ""
                this@AddPromotionViewModel.area = area ?: ""
                this@AddPromotionViewModel.category = category ?: ""
                _validationEvent.emit(true)
            }
        }
    }

    fun setDates(start: String, end: String) {
        this.startDate = start
        this.endDate = end
    }

    fun setScreenCount(count: Int) {
        this.screenCount1 = count
        logAllData()
    }

    private fun logAllData() {
        Log.d("AddPromotion", """
            Form Data:
            Your Name: $name
            Business Name: $businessName
            Mobile: $mobile
            City: $city
            Area: $area
            Category: $category
            Start Date: $startDate
            End Date: $endDate
            Screen Count: $screenCount1
        """.trimIndent())
    }

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