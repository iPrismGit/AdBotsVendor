package com.iprism.adbotsvendor.data.models.dropdowns

import com.google.gson.annotations.SerializedName

data class DropDownsRequest(

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("view_type")
	val viewType: String,

	@field:SerializedName("auth_token")
	val authToken: String,

	@field:SerializedName("city_id")
	val cityId: String
)
