package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalcilationApiResponse
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalculationRequest
import com.iprism.adbotsvendor.data.repositories.AnalyticsRepository
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(private val repository: AnalyticsRepository) : ViewModel() {

    private val _response = MutableStateFlow<UiState<PromotionCalcilationApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<PromotionCalcilationApiResponse>> = _response

    fun register(request: PromotionCalculationRequest) {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
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