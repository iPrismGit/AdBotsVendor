package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.User
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalcilationApiResponse
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalculationRequest
import com.iprism.adbotsvendor.data.repositories.AnalyticsRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val repository: AnalyticsRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _response = MutableStateFlow<UiState<PromotionCalcilationApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<PromotionCalcilationApiResponse>> = _response

    private var userCache: User? = null

    private suspend fun getUser(): User {
        return userCache ?: dataStoreManager.userDetails.first().also {
            userCache = it
        }
    }

    fun fetchCalculations(minutes: String, areas: String, categories: String) {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
                val user = getUser()
                val request = PromotionCalculationRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    minutes = minutes,
                    areas = areas,
                    categories = categories,
                    authToken = user.token ?: ""
                )
                Log.d("requestLoading", request.toString())
                val response = repository.fetchPromotionCalculation(request)
                if (response.status) {
                    _response.value = UiState.Success(response)
                } else {
                    _response.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _response.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}
