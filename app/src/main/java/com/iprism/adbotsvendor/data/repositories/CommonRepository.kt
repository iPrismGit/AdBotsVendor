package com.iprism.adbotsvendor.data.repositories

import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.home.HomePageRequest
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
import com.iprism.adbotsvendor.data.remote.HealthDrinksService
import javax.inject.Inject

class CommonRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun fetchHomePage(request: HomePageRequest) = apiService.fetchHomePage(request)
}