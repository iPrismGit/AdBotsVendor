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
	val sgst: Int,

	@field:SerializedName("wallet")
	val wallet: Int,

	@field:SerializedName("total_amount")
	val totalAmount: Int,

	@field:SerializedName("cgst")
	val cgst: Int
)

data class Response(

	@field:SerializedName("payment_details")
	val paymentDetails: PaymentDetails
)
