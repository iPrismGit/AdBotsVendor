package com.iprism.adbotsvendor.data.models.analytics

import com.google.gson.annotations.SerializedName

data class AnalyticsRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("view_type")
	val viewType: String,

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("auth_token")
	val authToken: String
)
