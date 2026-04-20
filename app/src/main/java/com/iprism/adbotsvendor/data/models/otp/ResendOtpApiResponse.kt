package com.iprism.adbotsvendor.data.models.otp

import com.google.gson.annotations.SerializedName

data class ResendOtpApiResponse(
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: OtpData
)

data class OtpData(
    @SerializedName("otp")
    val otp: String
)
