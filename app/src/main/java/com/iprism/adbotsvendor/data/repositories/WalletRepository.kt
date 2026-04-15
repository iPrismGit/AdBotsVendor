package com.iprism.adbotsvendor.data.repositories

import com.iprism.adbotsvendor.data.models.home.HomePageRequest
import com.iprism.adbotsvendor.data.models.wallet.WalletRequest
import com.iprism.adbotsvendor.data.remote.HealthDrinksService
import javax.inject.Inject

class WalletRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun wallet(request: WalletRequest) = apiService.wallet(request)
}