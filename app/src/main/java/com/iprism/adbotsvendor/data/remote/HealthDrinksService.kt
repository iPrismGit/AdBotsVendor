package com.iprism.adbotsvendor.data.remote

import com.iprism.adbotsvendor.data.models.LoginApiResponse
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.analytics.AnalyticsApiResponse
import com.iprism.adbotsvendor.data.models.analytics.AnalyticsRequest
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesApiResponse
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsApiResponse
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsRequest
import com.iprism.adbotsvendor.data.models.home.HomePageApiResponse
import com.iprism.adbotsvendor.data.models.home.HomePageRequest
import com.iprism.adbotsvendor.data.models.register.RegisterApiResponse
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
import com.iprism.adbotsvendor.data.models.wallet.WalletApiResponse
import com.iprism.adbotsvendor.data.models.wallet.WalletRequest
import com.iprism.adbotsvendor.data.models.wallethistory.WalletHistoryApiResponse
import com.iprism.adbotsvendor.data.models.wallethistory.WalletHistoryRequest
import com.iprism.adbotsvendor.utils.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface HealthDrinksService {

    @POST(Constants.LOGIN_ENDPOINT)
    suspend fun login(@Body request : LoginRequest) : LoginApiResponse

    @POST(Constants.REGISTER_ENDPOINT)
    suspend fun register(@Body request : RegisterRequest) : RegisterApiResponse

    @POST(Constants.CONTENT_PAGES_ENDPOINT)
    suspend fun fetchContentPage(@Body request : ContentPagesRequest) : ContentPagesApiResponse

    @POST(Constants.DROP_DOWNS_ENDPOINT)
    suspend fun fetchDropDowns(@Body request : DropDownsRequest) : DropDownsApiResponse

    @POST(Constants.HOME_PAGE_ENDPOINT)
    suspend fun fetchHomePage(@Body request : HomePageRequest) : HomePageApiResponse

    @POST(Constants.WALLET_ENDPOINT)
    suspend fun wallet(@Body request : WalletRequest) : WalletApiResponse

    @POST(Constants.WALLET_HISTORY_ENDPOINT)
    suspend fun fetchWalletHistory(@Body request : WalletHistoryRequest) : WalletHistoryApiResponse

    @POST(Constants.ANALYTICS_ENDPOINT)
    suspend fun fetchAnalytics(@Body request : AnalyticsRequest) : AnalyticsApiResponse
}