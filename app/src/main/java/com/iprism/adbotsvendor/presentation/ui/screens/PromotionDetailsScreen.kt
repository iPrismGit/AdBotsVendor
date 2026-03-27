package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White

@Composable
fun PromotionDetailsScreen(navController: NavHostController) {
    val redColor = Color(0xFFEF4444)
    val dividerColor = Color(0xFFEEEEEE)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(12.dp)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_img),
                contentDescription = "Back",
                tint = BLACK,
                modifier = Modifier.size(28.dp),
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Add 01",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = MontserratFamily,
                    color = Black
                )
                Text(
                    text = " (iPrism Add)",
                    fontSize = 20.sp,
                    color = Black,
                    fontFamily = MontserratFamily,
                    fontWeight = FontWeight.Normal
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Video Analytics",
                fontSize = 16.sp,
                color = BLACK1,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Data Rows
            AnalyticsRow(label = "Start Date", value = "21-03-2026")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

            AnalyticsRow(label = "End Date", value = "12-03-2026")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

            AnalyticsRow(label = "Total Days", value = "20 Days")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

            AnalyticsRow(label = "Price", value = "₹1000")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

            AnalyticsRow(label = "Play Time", value = "200 Minutes")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

            AnalyticsRow(label = "Screens", value = "30")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

            AnalyticsRow(label = "Area", value = "5 Locations")
            HorizontalDivider(thickness = 1.dp, color = dividerColor)

        }
        // Back Button

        // Extend Plan Button
        Button(
            onClick = { /* Extend plan logic */ },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = redColor)
        ) {
            Text(
                text = "Extend Plan",
                style = MaterialTheme.typography.labelMedium,
                color = White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
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
            color = Black
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Bold,
            color = Black
        )
    }
}


@Preview
@Composable
fun PromotionDetailsPreview() {
    PromotionDetailsScreen(
        navController = NavHostController(
            LocalContext
                .current
        )
    )
}