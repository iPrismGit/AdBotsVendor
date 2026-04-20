package com.iprism.adbotsvendor.data.models.profile

import com.google.gson.annotations.SerializedName

data class ProfileRequest(

	@field:SerializedName("area")
	val area: String,

	@field:SerializedName("user_id")
	val userId: Int,

	@field:SerializedName("bussiness_name")
	val bussinessName: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("view_type")
	val viewType: String,

	@field:SerializedName("auth_token")
	val authToken: String,

	@field:SerializedName("vendor_category")
	val vendorCategory: String
)
