package com.iprism.adbotsvendor.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.wallethistory.HistoryItem
import com.iprism.adbotsvendor.data.models.wallethistory.WalletHistoryRequest
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
class WalletHistoryViewModel @Inject constructor(
    private val repository: WalletRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _historyItems = MutableStateFlow<List<HistoryItem>>(emptyList())
    val historyItems: StateFlow<List<HistoryItem>> = _historyItems

    private val _uiState = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    val uiState: StateFlow<UiState<Unit>> = _uiState

    private var currentPage = 1
    private var isLastPage = false
    private var isFetching = false

    init {
        fetchWalletHistory()
    }

    fun fetchWalletHistory() {
        if (isFetching || isLastPage) return

        viewModelScope.launch {
            isFetching = true
            if (currentPage == 1) _uiState.value = UiState.Loading
            
            try {
                val user = dataStoreManager.userDetails.first()
                val request = WalletHistoryRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    page = currentPage,
                    authToken = user.token ?: ""
                )
                Log.d("requestLoading", request.toString())
                
                val response = repository.fetchWalletHistory(request)
                if (response.status) {
                    val newItems = response.response.history
                    if (newItems.isNotEmpty()) {
                        _historyItems.value += newItems
                        val totalPages = response.response.pagination.totalPages.size
                        if (currentPage >= totalPages) {
                            isLastPage = true
                        } else {
                            currentPage++
                        }
                    } else {
                        isLastPage = true
                    }
                    _uiState.value = UiState.Success(Unit)
                } else {
                    if (currentPage == 1) {
                        _uiState.value = UiState.Error(response.message)
                    }
                }
            } catch (e: Exception) {
                Log.e("WalletHistoryVM", "Error fetching history", e)
                if (currentPage == 1) {
                    _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
                }
            } finally {
                isFetching = false
            }
        }
    }
}
