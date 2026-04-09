package com.iprism.adbotsvendor.data.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(

	@field:SerializedName("player_id")
	val playerId: String,

	@field:SerializedName("app_version")
	val appVersion: String,

	@field:SerializedName("mobile")
	val mobile: String,

	@field:SerializedName("otp_confirmed")
	val otpConfirmed: String,

	@field:SerializedName("token")
	val token: String
)
