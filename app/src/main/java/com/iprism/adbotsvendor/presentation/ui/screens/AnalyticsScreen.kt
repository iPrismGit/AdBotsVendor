package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.analytics.PromotionsItem
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.DarkRed
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White
import com.iprism.adbotsvendor.presentation.viewmodels.AnalyticsViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun AnalyticsScreen(onNavigateToWallet : () -> Unit, onNavigateToNotifications : () -> Unit, onNavigateToPromotionDetails : () -> Unit, viewModel : AnalyticsViewModel = hiltViewModel()) {

    val analytics by viewModel.analytics.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(DarkBlue)
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    GradientDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    GradientDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    GradientDivider()
                }
                Spacer(modifier = Modifier.width(4.dp))
                Image(
                    painter = painterResource(R.drawable.add_bots_logo),
                    contentDescription = "Location",
                    modifier = Modifier.size(width = 120.dp, height = 60.dp),
                )

                Spacer(modifier = Modifier.width(4.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    GradientDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    GradientDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    GradientDivider()
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.location_img1),
                    contentDescription = "Location",
                    modifier = Modifier.size(46.dp),
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Hyderabad",
                            color = White,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Road No 4, Banjara Hills...",
                        color = White,
                        fontFamily = MontserratFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
                IconButton(onClick = { onNavigateToWallet() }) {
                    Image(
                        painter = painterResource(R.drawable.wallet_img),
                        contentDescription = "wallet",
                        modifier = Modifier.size(40.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = { onNavigateToNotifications() }) {
                    Image(
                        painter = painterResource(R.drawable.notifications_img),
                        contentDescription = "notifications",
                        modifier = Modifier
                            .size(40.dp)
                    )
                }
            }
        }
        GradientDivider()

        // Promotions Section
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Your Promotions",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkRed
            )

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(analytics) { index, promotion ->
                    if (index >= analytics.size - 1) {
                        viewModel.fetchAnalytics()
                    }
                    PromotionCardInAnalytics({ onNavigateToPromotionDetails() }, promotion)
                }

                if (isPaginationLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        }
                    }
                }
            }

            if (uiState is UiState.Error && analytics.isEmpty()) {
                Text(
                    text = (uiState as UiState.Error).message,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = BLACK
                )
            }
        }
    }

    if (uiState is UiState.Loading && analytics.isEmpty()) {
        LoadingScreen()
    }
}

@Composable
fun PromotionCardInAnalytics(onAnalyticsClick: () -> Unit, promotionsItem: PromotionsItem) {
    val cardGradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF015DC5), Color(0xFF559CEE))
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(cardGradient)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = promotionsItem.name,
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " (${promotionsItem.bussinessName})",
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start Date : ${promotionsItem.startDate}",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "End Date : ${promotionsItem.endDate}",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }

        Button(
            onClick = { onAnalyticsClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(32.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkRed)
        ) {
            Text(
                text = "View Analytics",
                fontSize = 12.sp,
                color = Color.White,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun GradientDivider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        White,
                        DarkRed,
                        White
                    )
                )
            )
    )
}