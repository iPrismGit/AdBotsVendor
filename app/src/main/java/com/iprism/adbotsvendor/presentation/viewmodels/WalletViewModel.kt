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
        wallet("", 0, "wallet")
    }

    fun wallet(transactionId : String, amount : Int, viewType : String) {
        viewModelScope.launch {
            if (viewType.equals("recharge_wallet", true)) {
                val error = validateAmount(amount)
                if (error != null) {
                    _event.emit(error)
                    return@launch
                }
            }
            _response.value = UiState.Loading
            try {
                val user = dataStoreManager.userDetails.first()
                val request = WalletRequest(transactionId, amount, user.userId?.toIntOrNull() ?: 0, viewType, user.token ?: "")
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

    private fun validateAmount(amount: Int): String? {
        if (amount <= 0) return "Amount must be greater than 0"
        if (amount < 10) return "Minimum recharge is ₹10"
        if (amount > 100000) return "Maximum limit is ₹10000"
        return null
    }
}