package com.iprism.adbotsvendor.data.models.home

import com.google.gson.annotations.SerializedName

data class HomePageApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class Response(

	@field:SerializedName("banners")
	val banners: List<BannersItem>
)

data class BannersItem(

	@field:SerializedName("updated_on")
	val updatedOn: Any,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("delete_status")
	val deleteStatus: String,

	@field:SerializedName("banner_link")
	val bannerLink: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String
)
