package com.iprism.adbotsvendor.data.repositories

import com.iprism.adbotsvendor.data.models.analytics.AnalyticsRequest
import com.iprism.adbotsvendor.data.models.promotiondetails.PromotionDetailsRequest
import com.iprism.adbotsvendor.data.remote.HealthDrinksService
import javax.inject.Inject

class AnalyticsRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun fetchAnalytics(request: AnalyticsRequest) = apiService.fetchAnalytics(request)

    suspend fun fetchPromotionDetails(request: PromotionDetailsRequest) = apiService.fetchPromotionDetails(request)
}