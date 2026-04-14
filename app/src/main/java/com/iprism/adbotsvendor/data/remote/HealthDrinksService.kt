package com.iprism.adbotsvendor.data.remote

import com.iprism.adbotsvendor.data.models.LoginApiResponse
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesApiResponse
import com.iprism.adbotsvendor.data.models.contentpages.ContentPagesRequest
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsApiResponse
import com.iprism.adbotsvendor.data.models.dropdowns.DropDownsRequest
import com.iprism.adbotsvendor.data.models.register.RegisterApiResponse
import com.iprism.adbotsvendor.data.models.register.RegisterRequest
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
}