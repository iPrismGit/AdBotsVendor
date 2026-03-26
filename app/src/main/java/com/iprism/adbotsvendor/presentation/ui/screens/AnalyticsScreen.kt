package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.DarkRed
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White

@Composable
fun AnalyticsScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(DarkBlue)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.location_img1),
                    contentDescription = "Location",
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Hyderabad",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "Road No 4, Banjara Hills...",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }

                Surface(
                    modifier = Modifier.size(45.dp),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.LocationOn,
                            contentDescription = "Wallet",
                            tint = DarkBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                // Notification Icon
                Surface(
                    modifier = Modifier.size(45.dp),
                    shape = CircleShape,
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = DarkBlue,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }

        // Promotions Section
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "Your Promotions",
                style = MaterialTheme.typography.headlineSmall,
                color = DarkRed
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(2) {
                    PromotionCardInAnalytics()
                }
            }
        }
    }
}

@Composable
fun PromotionCardInAnalytics() {
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
                    text = "Add 01",
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = " (iPrism Add)",
                    color = White,
                    fontFamily = MontserratFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Start Date : 21-03-2026",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "End Date : 21-04-2026",
                color = White,
                fontFamily = MontserratFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }

        Button(
            onClick = { /* Already in Analytics or nested view */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(32.dp),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkRed)
        ) {
            Text(text = "View Analytics", fontSize = 12.sp, color = Color.White, fontFamily = MontserratFamily, fontWeight = FontWeight.Medium)
        }
    }
}

@Preview
@Composable
fun AnalyticsScreenPreview() {
    AnalyticsScreen(
        navController = NavHostController(
            LocalContext
                .current
        )
    )
}
