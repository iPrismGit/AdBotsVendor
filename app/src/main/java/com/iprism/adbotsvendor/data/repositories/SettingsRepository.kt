package com.iprism.adbotsvendor.data.repositories

import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
import com.iprism.adbotsvendor.data.remote.HealthDrinksService
import javax.inject.Inject

class SettingsRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun fetchContentPage(request: ContentPagesRequest) = apiService.fetchContentPage(request)
}