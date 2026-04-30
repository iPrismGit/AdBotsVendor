package com.iprism.adbotsvendor.presentation.ui.screens

import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.promotioncalcilations.PromotionCalcilationApiResponse
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.LightBlue2
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey1
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White

import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.hilt.navigation.compose.hiltViewModel
import com.iprism.adbotsvendor.presentation.viewmodels.AddPromotionViewModel
import com.iprism.adbotsvendor.presentation.viewmodels.PreviewViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun PreviewScreen(
    navController: NavHostController,
    viewModel: AddPromotionViewModel,
    previewViewModel: PreviewViewModel = hiltViewModel()
) {
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val calculationState by previewViewModel.response.collectAsStateWithLifecycle()
    val addPromotionState by previewViewModel.addPromotionResponse.collectAsStateWithLifecycle()
    
    var isWalletUsed by remember { mutableStateOf(false) }
    var isTermsAccepted by remember { mutableStateOf(false) }
    var showVideoPreview by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        previewViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(addPromotionState) {
        if (addPromotionState is UiState.Success) {
            Toast.makeText(context, "Promotion added successfully!", Toast.LENGTH_SHORT).show()
            navController.navigate("home") {
                popUpTo("preview") { inclusive = true }
            }
        } else if (addPromotionState is UiState.Error) {
            Toast.makeText(context, (addPromotionState as UiState.Error).message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(formState.areaId, formState.categoryId) {
        if (formState.areaId.isNotEmpty() && formState.categoryId.isNotEmpty()) {
            previewViewModel.fetchCalculations(
                minutes = "1",
                areas = formState.areaId,
                categories = formState.categoryId
            )
        }
    }

    val fileName = remember(formState.videoUri) {
        formState.videoUri?.let { uriString ->
            val uri = Uri.parse(uriString)
            var result: String? = null
            if (uri.scheme == "content") {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.use {
                    if (it.moveToFirst()) {
                        val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
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
            result
        } ?: "No file selected"
    }

    val paymentDetails = (calculationState as? UiState.Success<PromotionCalcilationApiResponse>)?.data?.response?.paymentDetails
    val totalAmount = paymentDetails?.totalAmount ?: 0
    val walletAmount = paymentDetails?.wallet ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
    ) {
        // Back Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_img),
                contentDescription = "Back",
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Preview",
                style = MaterialTheme.typography.headlineSmall,
                color = BLACK
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Charges Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFF015DC5), Color(0xFF357ABD))))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Charges", color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = MontserratFamily)
                        Text("₹$totalAmount", color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = MontserratFamily)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Start Date : ${formState.startDate}", color = White, fontSize = 12.sp, fontFamily = MontserratFamily, fontWeight = FontWeight.Light)
                }
                
                Button(
                    onClick = { /* Change Date */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30))
                ) {
                    Text("Change Date", fontSize = 10.sp, fontWeight = FontWeight.Medium, fontFamily = MontserratFamily, color = White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Promotional Video", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = BLACK, fontFamily = MontserratFamily)
            Spacer(modifier = Modifier.height(12.dp))

            // File Selector Placeholder
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id =R.drawable.cross_img),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(fileName, modifier = Modifier.weight(1f), fontSize = 12.sp, color = BLACK1, fontFamily = MontserratFamily, fontWeight = FontWeight.Normal)
                Text(
                    "Preview",
                    color = LightBlue2,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = MontserratFamily,
                    modifier = Modifier.clickable { showVideoPreview = true }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Wallet Amount Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.wallet_img1),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Wallet Amount", fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = MontserratFamily, color = BLACK)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Use Wallet Funds", fontSize = 10.sp, color = BLACK, fontFamily = MontserratFamily, fontWeight = FontWeight.Normal)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("₹$walletAmount", fontWeight = FontWeight.Normal, fontSize = 14.sp, fontFamily = MontserratFamily, color = BLACK)
                    }
                    Checkbox(
                        checked = isWalletUsed,
                        onCheckedChange = { isWalletUsed = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Contact Details", fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = MontserratFamily, color = BLACK)
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Ad Name", style = MaterialTheme.typography.bodySmall, color = BLACK1)
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(formState.name, "", onValueChange = {})

            Spacer(modifier = Modifier.height(30.dp))
            
            // Terms Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it },
                    modifier = Modifier.offset(y = (-12).dp)
                )
                Text(
                    text = "By clicking continue, I accept the terms of service & privacy policy",
                    fontSize = 12.sp,
                    fontFamily = MontserratFamily,
                    fontWeight = FontWeight.Normal,
                    color = LightBlue2,
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }

        if (showVideoPreview && formState.videoUri != null) {
            VideoPreviewDialog(
                videoUri = formState.videoUri!!,
                onDismiss = { showVideoPreview = false }
            )
        }

        // Continue Button
        Button(
            onClick = {
                val pDetails = (calculationState as? UiState.Success)?.data?.response?.paymentDetails
                val currentWalletAmount = pDetails?.wallet ?: 0
                val usedWalletAmount = if (isWalletUsed) currentWalletAmount else 0
                val remainingAmountValue = totalAmount - usedWalletAmount

                previewViewModel.submitPromotion(
                    context = context,
                    name = formState.name,
                    businessName = formState.businessName,
                    mobile = formState.mobile,
                    cityId = formState.cityId,
                    areaId = formState.areaId,
                    categoryId = formState.categoryId,
                    startDate = formState.startDate,
                    endDate = formState.endDate,
                    screenCount = formState.screenCount,
                    totalAmount = totalAmount.toString(),
                    walletAmount = usedWalletAmount.toString(),
                    remainingAmount = remainingAmountValue.toString(),
                    sgst = (pDetails?.sgst ?: 0).toString(),
                    cgst = (pDetails?.cgst ?: 0).toString(),
                    videoUri = formState.videoUri?.let { Uri.parse(it) },
                    categoriesCount = 1,
                    transactionId = "0",
                    playTime = "1",
                    areasCount = 1
                )
            },
            enabled = isTermsAccepted && addPromotionState !is UiState.Loading,
            modifier = Modifier
                .fillMaxWidth().padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkBlue,
                disabledContainerColor = Color.LightGray
            )
        ) {
            if (addPromotionState is UiState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = White,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Continue", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
            }
        }
    }

    if (calculationState is UiState.Loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (calculationState is UiState.Error) {
        // Show error message if needed
    }
}
