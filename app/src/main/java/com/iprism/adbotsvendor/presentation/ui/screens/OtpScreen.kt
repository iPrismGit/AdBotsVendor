package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.navigation.Screen
import com.iprism.adbotsvendor.presentation.ui.components.OTPView
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.Green
import com.iprism.adbotsvendor.presentation.ui.theme.Grey
import com.iprism.adbotsvendor.presentation.ui.theme.LightBlack
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily

@Composable
fun OtpScreen(navController: NavHostController, otp: String = "", mobile: String = "") {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_img),
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "AdBots Logo",
                modifier = Modifier.fillMaxWidth(0.6f)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .border(1.dp, Color(0xFFF5F5F5), RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Enter your code",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "+91 $mobile",
                    style = MaterialTheme.typography.titleMedium,
                    color = Grey
                )
                Spacer(modifier = Modifier.height(32.dp))
                OTPView(4) { }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        Text(
                            text = "Didn't receive the code? ",
                            style = MaterialTheme.typography.bodySmall,
                            color = LightBlack
                        )
                        Text(
                            text = "Resend",
                            color = Green,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable { /* Resend logic */ }
                        )
                    }
                    Text(
                        text = "00:26",
                        style = MaterialTheme.typography.bodySmall,
                        color = LightBlack
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = { navController.navigate(Screen.Register.route) },
                    modifier = Modifier
                        .fillMaxWidth().imePadding(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                ) {
                    Text(
                        text = "Continue",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}