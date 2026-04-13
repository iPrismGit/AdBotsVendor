package com.iprism.adbotsvendor.data.models.register

import com.google.gson.annotations.SerializedName

data class RegisterApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class VendorDetailsItem(

	@field:SerializedName("transaction_Id")
	val transactionId: String,

	@field:SerializedName("registered_by")
	val registeredBy: String,

	@field:SerializedName("updated_on")
	val updatedOn: String,

	@field:SerializedName("area")
	val area: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("mobile")
	val mobile: String,

	@field:SerializedName("wallet_balance")
	val walletBalance: String,

	@field:SerializedName("vendor_category")
	val vendorCategory: String,

	@field:SerializedName("token")
	val token: String,

	@field:SerializedName("video_upload")
	val videoUpload: String,

	@field:SerializedName("player_id")
	val playerId: String,

	@field:SerializedName("bussiness_name")
	val bussinessName: String,

	@field:SerializedName("total_amount")
	val totalAmount: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("delete_status")
	val deleteStatus: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("paid_amount")
	val paidAmount: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("time")
	val time: String,

	@field:SerializedName("auth_token")
	val authToken: String,

	@field:SerializedName("status")
	val status: String
)

data class Response(

	@field:SerializedName("vendor_details")
	val vendorDetails: List<VendorDetailsItem>
)
