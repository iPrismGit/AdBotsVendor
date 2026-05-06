package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.viewmodels.PromotionDetailsViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun PromotionDetailsScreen(
    onBack : () -> Unit,
    viewModel: PromotionDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.response.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 12.dp)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { onBack() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_img),
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(28.dp),
            )
        }

        when (state) {
            is UiState.Success -> {
                val details = (state as UiState.Success).data.response
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = details.name,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = MontserratFamily,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = " (${details.bussinessName})",
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Normal
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Promotion Analytics",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Data Rows
                    AnalyticsRow(label = "Start Date", value = details.startDate)
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    AnalyticsRow(label = "End Date", value = details.endDate)
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    AnalyticsRow(label = "Total Days", value = "${details.noOfDays} Days")
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    AnalyticsRow(label = "Price", value = "₹${details.totalAmount}")
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    AnalyticsRow(label = "Play Time", value = "${details.playTime} Minutes")
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    AnalyticsRow(label = "Screens", value = details.screeens)
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)

                    AnalyticsRow(label = "Area", value = "${details.areasCount} Locations")
                    HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.outlineVariant)
                }

                Button(
                    onClick = { /* Extend plan logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "Extend Plan",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = (state as UiState.Error).message, color = Color.Red)
                }
            }

            else -> {}
        }
    }

    if (state is UiState.Loading) {
        LoadingScreen()
    }
}

@Composable
fun AnalyticsRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}