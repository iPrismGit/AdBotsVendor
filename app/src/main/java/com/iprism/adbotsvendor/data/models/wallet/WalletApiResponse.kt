package com.iprism.adbotsvendor.data.models.wallet

import com.google.gson.annotations.SerializedName

data class WalletApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class Response(

	@field:SerializedName("wallet")
	val wallet: String
)
