package com.iprism.adbotsvendor.presentation.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.presentation.ui.components.OTPView
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.Green
import com.iprism.adbotsvendor.presentation.ui.theme.Grey
import com.iprism.adbotsvendor.presentation.ui.theme.LightBlack
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.viewmodels.OtpViewModel
import com.iprism.adbotsvendor.utils.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OtpScreen(
    otp: String = "",
    mobile: String = "",
    onBack : () -> Unit,
    onNavigateToRegister : () -> Unit,
    onNavigateToHome : () -> Unit,
    viewModel: OtpViewModel = hiltViewModel()
) {
    var enteredOtp by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val state by viewModel.loginResponse.collectAsStateWithLifecycle()

    var timerText by remember { mutableStateOf("00:30") }
    var isResendEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isResendEnabled) {
        if (!isResendEnabled) {
            var timeLeft = 30
            while (timeLeft > 0) {
                delay(1000L)
                timeLeft--
                timerText = String.format("00:%02d", timeLeft)
            }
            isResendEnabled = true
        }
    }

    LaunchedEffect(Unit) {
        Log.d("eventState", "coming")
        viewModel.eventFlow.collectLatest { event ->
            Log.d("eventState", "coming1")
            when (event) {
                is OtpViewModel.UiEvent.NavigateToRegister -> {
                    onNavigateToRegister()
                }
                is OtpViewModel.UiEvent.NavigateToHome -> {
                    onNavigateToHome()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { onBack() },
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
                .border(
                    1.dp,
                    Color(0xFFF5F5F5),
                    RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ),
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
                OTPView(4) {
                    enteredOtp = it
                }
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
                            color = if (isResendEnabled) Green else Color.Gray,
                            fontFamily = MontserratFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            modifier = Modifier.clickable(enabled = isResendEnabled) {
                                isResendEnabled = false
                                /* Resend logic */
                            }
                        )
                    }
                    Text(
                        text = timerText,
                        style = MaterialTheme.typography.bodySmall,
                        color = LightBlack
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Button(
                    onClick = {
                        if (otp == enteredOtp) {
                            val request = LoginRequest(
                                playerId = "",
                                appVersion = "",
                                mobile = mobile,
                                otpConfirmed = "yes",
                                token = "token"
                            )
                            viewModel.login(request)
                        } else {
                            Toast.makeText(context, "Invalid OTP", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    shape = RoundedCornerShape(12.dp),
                    enabled = state !is UiState.Loading,
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                ) {
                    if (state is UiState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "Continue",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}