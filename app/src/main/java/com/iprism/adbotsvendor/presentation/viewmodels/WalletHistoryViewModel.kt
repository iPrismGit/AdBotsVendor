package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesApiResponse
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.wallethistory.WalletHistoryApiResponse
import com.iprism.adbotsvendor.data.models.wallethistory.WalletHistoryRequest
import com.iprism.adbotsvendor.data.repositories.SettingsRepository
import com.iprism.adbotsvendor.data.repositories.WalletRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletHistoryViewModel @Inject constructor(private val repository: WalletRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _response = MutableStateFlow<UiState<WalletHistoryApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<WalletHistoryApiResponse>> = _response

    fun fetchWalletHistory() {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = WalletHistoryRequest(user.userId?.toIntOrNull() ?: 0, 1, user.token ?: "")
                Log.d("requestLoading", request.toString())
                val response = repository.fetchWalletHistory(request)
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