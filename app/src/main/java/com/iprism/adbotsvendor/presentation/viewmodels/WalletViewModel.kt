package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesApiResponse
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.wallet.WalletApiResponse
import com.iprism.adbotsvendor.data.models.wallet.WalletRequest
import com.iprism.adbotsvendor.data.repositories.SettingsRepository
import com.iprism.adbotsvendor.data.repositories.WalletRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalletViewModel @Inject constructor(private val repository: WalletRepository, private val dataStoreManager: DataStoreManager) : ViewModel() {

    private val _response = MutableStateFlow<UiState<WalletApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<WalletApiResponse>> = _response

    private val _event = MutableSharedFlow<String>()
    val event = _event

    init {
        wallet()
    }

    fun wallet() {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = WalletRequest("", "", user.userId?.toIntOrNull() ?: 0,"wallet", user.token ?: "")
                Log.d("requestLoading", request.toString())
                val response = repository.wallet(request)
                if (response.status) {
                    _response.value = UiState.Success(response)
                } else {
                    _response.value = UiState.Error(response.message)
                    _event.emit(response.message ?: "Something went wrong")
                }
            } catch (e: Exception) {
                _response.value = UiState.Error(e.localizedMessage ?: "Unknown error")
                _event.emit(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}