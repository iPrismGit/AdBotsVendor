package com.iprism.adbotsvendor.data.models.analytics

import com.google.gson.annotations.SerializedName

data class AnalyticsApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class PromotionsItem(

	@field:SerializedName("updated_on")
	val updatedOn: Any,

	@field:SerializedName("end_date")
	val endDate: String,

	@field:SerializedName("sgst")
	val sgst: String,

	@field:SerializedName("transaction_Id")
	val transactionId: String,

	@field:SerializedName("remainimg_amount")
	val remainimgAmount: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("mobile")
	val mobile: String,

	@field:SerializedName("cgst")
	val cgst: String,

	@field:SerializedName("play_time")
	val playTime: String,

	@field:SerializedName("bussiness_name")
	val bussinessName: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("total_amount")
	val totalAmount: String,

	@field:SerializedName("vendor_id")
	val vendorId: String,

	@field:SerializedName("no_of_days")
	val noOfDays: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("ad_link")
	val adLink: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("start_date")
	val startDate: String,

	@field:SerializedName("screeens")
	val screeens: String,

	@field:SerializedName("wallet_amount")
	val walletAmount: String
)

data class TotalPagesItem(

	@field:SerializedName("page")
	val page: Int
)

data class Response(

	@field:SerializedName("promotions")
	val promotions: ArrayList<PromotionsItem>,

	@field:SerializedName("pagination")
	val pagination: Pagination
)

data class Pagination(

	@field:SerializedName("limit")
	val limit: Int,

	@field:SerializedName("total_pages")
	val totalPages: List<TotalPagesItem>,

	@field:SerializedName("current_page")
	val currentPage: Int
)
