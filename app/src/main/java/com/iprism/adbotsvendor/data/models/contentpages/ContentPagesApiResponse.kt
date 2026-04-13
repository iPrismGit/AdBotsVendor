package com.iprism.adbotsvendor.data.models.contentpages

import com.google.gson.annotations.SerializedName

data class ContentPagesApiResponse(

	@field:SerializedName("response")
	val response: List<ResponseItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class ResponseItem(

	@field:SerializedName("updated_on")
	val updatedOn: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("content")
	val content: String
)
