package com.iprism.adbotsvendor.data.models.otp

import com.google.gson.annotations.SerializedName

data class OtpRequest(
    @SerializedName("mobile")
    val mobile: String
)
