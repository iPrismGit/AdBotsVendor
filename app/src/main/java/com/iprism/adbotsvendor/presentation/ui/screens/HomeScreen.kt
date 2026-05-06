package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.Banners
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.LightBlue1
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.Red
import com.iprism.adbotsvendor.presentation.ui.theme.White
import com.iprism.adbotsvendor.presentation.viewmodels.HomeViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun HomeScreen(
    onNavigateToWallet: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToPromotion: () -> Unit,
    homeViewModel: HomeViewModel
) {

    val scrollState = rememberScrollState()
    val state by homeViewModel.response.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .statusBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
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
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        text = "Road No 4, Banjara Hills...",
                        color = MaterialTheme.colorScheme.onPrimary,
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(R.drawable.home_ad_img),
                contentDescription = null,
                modifier = Modifier
                    .width(220.dp)
                    .height(120.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Grow your brand with us.",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Advertise your business on high-visibility digital screens and reach thousands of people every day. Turn attention into customers with powerful visual promotions. Turn attention into customers with powerful visual promotion",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { onNavigateToAnalytics() },
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = "View More",
                        fontFamily = MontserratFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                }

                Button(
                    onClick = { onNavigateToPromotion() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(
                        text = "Promote now",
                        fontFamily = MontserratFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                    )
                }
            }
        }

        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (state is UiState.Success) {
                Banners((state as UiState.Success).data.response.banners)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}