package com.iprism.adbotsvendor.data.models.wallethistory

import com.google.gson.annotations.SerializedName

data class WalletHistoryApiResponse(

	@field:SerializedName("response")
	val response: Response,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class TotalPagesItem(

	@field:SerializedName("page")
	val page: Int
)

data class Response(

	@field:SerializedName("pagination")
	val pagination: Pagination,

	@field:SerializedName("history")
	val history: List<HistoryItem>
)

data class HistoryItem(

	@field:SerializedName("transaction_id")
	val transactionId: String,

	@field:SerializedName("amount")
	val amount: String,

	@field:SerializedName("balance_after")
	val balanceAfter: String,

	@field:SerializedName("created_on")
	val createdOn: String,

	@field:SerializedName("vendor_id")
	val vendorId: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("type")
	val type: String,

	@field:SerializedName("transaction_type")
	val transactionType: String,

	@field:SerializedName("balance_before")
	val balanceBefore: String
)

data class Pagination(

	@field:SerializedName("limit")
	val limit: Int,

	@field:SerializedName("total_pages")
	val totalPages: List<TotalPagesItem>,

	@field:SerializedName("current_page")
	val currentPage: Int
)
