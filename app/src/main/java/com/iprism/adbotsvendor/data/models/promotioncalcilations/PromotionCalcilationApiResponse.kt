package com.iprism.adbotsvendor.data.models.promotioncalcilations

import com.google.gson.annotations.SerializedName

data class PromotionCalcilationApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class PaymentDetails(

	@field:SerializedName("sgst")
	val sGst: String,

	@field:SerializedName("wallet")
	val wallet: String,

	@field:SerializedName("total_amount")
	val totalAmount: String,

	@field:SerializedName("cgst")
	val cGst: String,

	@field:SerializedName("igst")
	val iGst: String,

	@field:SerializedName("grand_total")
	val grandTotal: String
)

data class Response(

	@field:SerializedName("payment_details")
	val paymentDetails: PaymentDetails
)
