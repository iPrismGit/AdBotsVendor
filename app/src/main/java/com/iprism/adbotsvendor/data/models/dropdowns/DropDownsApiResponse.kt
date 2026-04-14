package com.iprism.adbotsvendor.data.models.dropdowns

import com.google.gson.annotations.SerializedName

data class DropDownsApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class CategoriesItem(

	@field:SerializedName("updated_on")
	val updatedOn: String,

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("delete_status")
	val deleteStatus: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String
)

data class Response(

	@field:SerializedName("cities")
	val cities: List<CitiesItem>,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem>
)

data class CitiesItem(

	@field:SerializedName("updated_on")
	val updatedOn: Any,

	@field:SerializedName("city_name")
	val cityName: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("delete_status")
	val deleteStatus: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String
)
