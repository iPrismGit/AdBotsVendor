package com.iprism.adbotsvendor.data.repositories

import com.iprism.adbotsvendor.data.models.analytics.AnalyticsRequest
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalculationRequest
import com.iprism.adbotsvendor.data.models.promotiondetails.PromotionDetailsRequest
import com.iprism.adbotsvendor.data.remote.HealthDrinksService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class AnalyticsRepository @Inject constructor(private val apiService : HealthDrinksService) {

    suspend fun fetchAnalytics(request: AnalyticsRequest) = apiService.fetchAnalytics(request)

    suspend fun fetchPromotionDetails(request: PromotionDetailsRequest) = apiService.fetchPromotionDetails(request)

    suspend fun fetchPromotionCalculation(request: PromotionCalculationRequest) = apiService.fetchPromotionCalculations(request)

    suspend fun addPromotion(
        token: RequestBody,
        userId: RequestBody,
        name: RequestBody,
        areas: RequestBody,
        areasCount: RequestBody,
        categories: RequestBody,
        categoriesCount: RequestBody,
        city: RequestBody,
        startDate: RequestBody,
        endDate: RequestBody,
        screens: RequestBody,
        totalAmount: RequestBody,
        walletAmount: RequestBody,
        grandTotal: RequestBody,
        sgst: RequestBody,
        cgst: RequestBody,
        igst: RequestBody,
        playTime: RequestBody,
        transactionId: RequestBody,
        noOfDays: RequestBody,
        videoLength: RequestBody,
        vendorVideo: MultipartBody.Part
    ) = apiService.addPromotion(
        token, userId, name, areas, areasCount, categories, categoriesCount, city, startDate, endDate, screens,
        totalAmount, walletAmount, grandTotal, sgst, cgst, igst, playTime, transactionId, noOfDays, videoLength, vendorVideo
    )
}
