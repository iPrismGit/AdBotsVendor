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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.home.BannersItem
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
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel = hiltViewModel()) {

    val scrollState = rememberScrollState()
    val state by  homeViewModel.response.collectAsStateWithLifecycle()
    var banners by remember { mutableStateOf<List<BannersItem>>(emptyList()) }

    LaunchedEffect(Unit) {
        homeViewModel.fetchHomePage()
    }
    LaunchedEffect(state) {
        when(state) {
            is UiState.Success -> {
                banners = (state as UiState.Success).data.response.banners
            }
            is UiState.Error -> {
            }
            is UiState.Loading -> {
            }
            else -> {}
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(scrollState)
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
                IconButton(onClick = { navController.navigate("wallet") }) {
                    Image(
                        painter = painterResource(R.drawable.wallet_img),
                        contentDescription = "wallet",
                        modifier = Modifier.size(40.dp),
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                IconButton(onClick = { navController.navigate("notifications") }) {
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
                .background(LightBlue1)
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
                color = Red,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Advertise your business on high-visibility digital screens and reach thousands of people every day. Turn attention into customers with powerful visual promotions. Turn attention into customers with powerful visual promotion",
                style = MaterialTheme.typography.bodySmall,
                color = BLACK1
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = { navController.navigate("analytics") },
                    modifier = Modifier
                        .weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, DarkBlue),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = DarkBlue),
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
                    onClick = { navController.navigate("business_details") },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
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
            Banners(banners)
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}