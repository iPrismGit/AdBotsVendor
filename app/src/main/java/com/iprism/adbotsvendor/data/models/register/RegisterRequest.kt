package com.iprism.adbotsvendor.data.models.register

import com.google.gson.annotations.SerializedName

data class RegisterRequest(

	@field:SerializedName("area")
	val area: String,

	@field:SerializedName("user_id")
	val userId: String,

	@field:SerializedName("bussiness_name")
	val bussinessName: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("vendor_category")
	val vendorCategory: String
)
