package com.iprism.adbotsvendor.data.models.contentpages

import com.google.gson.annotations.SerializedName

data class ContentPagesRequest(

	@field:SerializedName("view_type")
	val viewType: String
)
