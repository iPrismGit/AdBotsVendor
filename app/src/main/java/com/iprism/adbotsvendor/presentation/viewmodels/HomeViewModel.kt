package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesApiResponse
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.home.HomePageApiResponse
import com.iprism.adbotsvendor.data.models.home.HomePageRequest
import com.iprism.adbotsvendor.data.repositories.CommonRepository
import com.iprism.adbotsvendor.data.repositories.SettingsRepository
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CommonRepository) : ViewModel() {

    private val _response = MutableStateFlow<UiState<HomePageApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<HomePageApiResponse>> = _response

    fun register(request: HomePageRequest) {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
                Log.d("requestLoading", request.toString())
                val response = repository.fetchHomePage(request)
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