package com.iprism.adbotsvendor.presentation.viewmodels

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iprism.adbotsvendor.data.models.User
import com.iprism.adbotsvendor.data.models.addpromotion.AddPromotionApiResponse
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalcilationApiResponse
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalculationRequest
import com.iprism.adbotsvendor.data.repositories.AnalyticsRepository
import com.iprism.adbotsvendor.utils.DataStoreManager
import com.iprism.adbotsvendor.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val repository: AnalyticsRepository,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _response = MutableStateFlow<UiState<PromotionCalcilationApiResponse>>(UiState.Idle)
    val response: StateFlow<UiState<PromotionCalcilationApiResponse>> = _response

    private val _addPromotionResponse = MutableStateFlow<UiState<AddPromotionApiResponse>>(UiState.Idle)
    val addPromotionResponse: StateFlow<UiState<AddPromotionApiResponse>> = _addPromotionResponse

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    private var userCache: User? = null

    private suspend fun getUser(): User {
        return userCache ?: dataStoreManager.userDetails.first().also {
            userCache = it
        }
    }

    fun fetchCalculations(minutes: String, areas: String, categories: String, startDate: String, noOfScreens: String, city: String) {
        viewModelScope.launch {
            _response.value = UiState.Loading
            try {
                val user = getUser()
                val request = PromotionCalculationRequest(
                    userId = user.userId?.toIntOrNull() ?: 0,
                    minutes = minutes,
                    areas = areas,
                    categories = categories,
                    authToken = user.token ?: "",
                    startDate = startDate,
                    noOfScreens = noOfScreens,
                    city = city
                )
                Log.d("requestLoading", request.toString())
                val response = repository.fetchPromotionCalculation(request)
                if (response.status) {
                    _response.value = UiState.Success(response)
                } else {
                    _response.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _response.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    fun submitPromotion(
        context: Context,
        name: String,
        businessName: String,
        mobile: String,
        cityId: String,
        areaId: String,
        categoryId: String,
        startDate: String,
        endDate: String,
        screenCount: Int,
        totalAmount: String,
        walletAmount: String,
        remainingAmount: String,
        sgst: String,
        cgst: String,
        videoUri: Uri?,
        categoriesCount: Int,
        transactionId: String,
        playTime : String,
        areasCount : Int
    ) {
        viewModelScope.launch {
            if (videoUri == null) {
                _toastMessage.emit("Please select a video")
                return@launch
            }

            _addPromotionResponse.value = UiState.Loading
            try {
                val user = getUser()
                val videoFile = getFileFromUri(context, videoUri)
                if (videoFile == null) {
                    _addPromotionResponse.value = UiState.Error("Failed to process video file")
                    return@launch
                }

                val requestFile = videoFile.asRequestBody("video/mp4".toMediaTypeOrNull())
                val vendorVideoPart = MultipartBody.Part.createFormData("vendor_video", videoFile.name, requestFile)

                val tokenBody = (user.token ?: "").toRequestBody("text/plain".toMediaTypeOrNull())
                val userIdBody = user.userId?.toRequestBody("text/plain".toMediaTypeOrNull()) ?: "".toRequestBody("text/plain".toMediaTypeOrNull())
                val nameBody = name.toRequestBody("text/plain".toMediaTypeOrNull())
                val businessNameBody = businessName.toRequestBody("text/plain".toMediaTypeOrNull())
                val mobileBody = mobile.toRequestBody("text/plain".toMediaTypeOrNull())
                val areasBody = areaId.toRequestBody("text/plain".toMediaTypeOrNull())
                val areasCountBody = areasCount.toString().toRequestBody("text/plain".toMediaTypeOrNull()) // Assuming 1 for now
                val categoriesBody = categoryId.toRequestBody("text/plain".toMediaTypeOrNull())
                val startDateBody = startDate.toRequestBody("text/plain".toMediaTypeOrNull())
                val endDateBody = endDate.toRequestBody("text/plain".toMediaTypeOrNull())
                val screensBody = screenCount.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                val totalAmountBody = totalAmount.toRequestBody("text/plain".toMediaTypeOrNull())
                val walletAmountBody = walletAmount.toRequestBody("text/plain".toMediaTypeOrNull())
                val remainingAmountBody = remainingAmount.toRequestBody("text/plain".toMediaTypeOrNull())
                val sgstBody = sgst.toRequestBody("text/plain".toMediaTypeOrNull())
                val cgstBody = cgst.toRequestBody("text/plain".toMediaTypeOrNull())
                val playTimeBody = playTime.toRequestBody("text/plain".toMediaTypeOrNull())
                val transactionIdBody = transactionId.toRequestBody("text/plain".toMediaTypeOrNull())
                val cityBody = cityId.toRequestBody("text/plain".toMediaTypeOrNull())
                val categoriesCountBody = categoriesCount.toString().toRequestBody("text/plain".toMediaTypeOrNull())

                Log.d("AddPromotionRequest", """
                    userId: ${user.userId}
                    name: $name
                    businessName: $businessName
                    mobile: $mobile
                    areas: $areaId
                    areasCount: $areasCount
                    categories: $categoryId
                    startDate: $startDate
                    endDate: $endDate
                    screens: $screenCount
                    totalAmount: $totalAmount
                    walletAmount: $walletAmount
                    remainingAmount: $remainingAmount
                    sgst: $sgst
                    cgst: $cgst
                    playTime: $playTime
                    transactionId: $transactionId
                    city: $cityId
                    categoriesCount: $categoriesCount
                    videoFile: ${videoFile.absolutePath}
                """.trimIndent())

                Log.d("AddPromotionRequest", "Auth Token: ${user.token}")

                val response = repository.addPromotion(
                    tokenBody, userIdBody, nameBody, businessNameBody, mobileBody, areasBody, areasCountBody,
                    categoriesBody, startDateBody, endDateBody, screensBody, totalAmountBody,
                    walletAmountBody, remainingAmountBody, sgstBody, cgstBody, playTimeBody,
                    transactionIdBody, cityBody, categoriesCountBody, vendorVideoPart
                )

                if (response.status) {
                    _addPromotionResponse.value = UiState.Success(response)
                } else {
                    _addPromotionResponse.value = UiState.Error(response.message)
                }
            } catch (e: Exception) {
                _addPromotionResponse.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }

    private fun getFileFromUri(context: Context, uri: Uri): File? {
        return try {
            val contentResolver = context.contentResolver
            val fileName = getFileName(context, uri) ?: "temp_video"
            val file = File(context.cacheDir, fileName)
            contentResolver.openInputStream(uri)?.use { inputStream ->
                file.outputStream().use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            file
        } catch (e: Exception) {
            null
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val index = it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME)
                    if (index != -1) {
                        result = it.getString(index)
                    }
                }
            }
        }
        if (result == null) {
            result = uri.path
            val cut = result?.lastIndexOf('/') ?: -1
            if (cut != -1) {
                result = result?.substring(cut + 1)
            }
        }
        return result
    }
}
