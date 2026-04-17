package com.iprism.adbotsvendor.data.models.addpromotion

import com.google.gson.annotations.SerializedName

data class AddPromotionApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class Response(
	val any: Any? = null
)
