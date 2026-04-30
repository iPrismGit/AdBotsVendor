package com.iprism.adbotsvendor.data.models.promotioncalcilations

import com.google.gson.annotations.SerializedName

data class PromotionCalculationRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("minutes")
	val minutes: String,

	@field:SerializedName("areas")
	val areas: String,

	@field:SerializedName("categories")
	val categories: String,

	@field:SerializedName("auth_token")
	val authToken: String,

	@field:SerializedName("start_date")
	val startDate: String,

	@field:SerializedName("no_of_screens")
	val noOfScreens: String,

	@field:SerializedName("city")
	val city: String
)
