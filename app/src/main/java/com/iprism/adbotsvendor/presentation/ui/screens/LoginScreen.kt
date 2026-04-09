package com.iprism.adbotsvendor.presentation.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.LoginRequest
import com.iprism.adbotsvendor.presentation.ui.theme.BorderGrey
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.Grey555
import com.iprism.adbotsvendor.presentation.viewmodels.LoginViewModel
import com.iprism.adbotsvendor.utils.UiState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    onNavigateToOtp: (otp: String, mobile: String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var mobileNumber by rememberSaveable { mutableStateOf("") }
    val loginState by viewModel.loginResponse.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is LoginViewModel.LoginEvent.NavigateToOtp -> {
                    onNavigateToOtp(event.otp, event.mobile)
                    Toast.makeText(context, event.otp, Toast.LENGTH_SHORT).show()
                }
                is LoginViewModel.LoginEvent.Error -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "AdBots Logo",
                modifier = Modifier.size(180.dp)
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .border(1.dp, BorderGrey, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
            color = Color.White,
            tonalElevation = 2.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Mobile Number",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.india_flag),
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .padding(start = 16.dp, end = 16.dp)
                    )

                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp),
                        color = Color.LightGray
                    )

                    TextField(
                        value = mobileNumber,
                        onValueChange = {
                            if (it.length <= 10 && it.all { char -> char.isDigit() }) {
                                mobileNumber = it
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodySmall,
                        placeholder = {
                            Text(
                                "Enter Mobile Number",
                                color = Grey555,
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "By clicking, I accept the terms of service and privacy policy",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (mobileNumber.length == 10) {
                            val request = LoginRequest(
                                playerId = "",
                                appVersion = "1.0",
                                mobile = mobileNumber,
                                otpConfirmed = "no",
                                token = "token"
                            )
                            viewModel.login(request)
                        } else {
                            Toast.makeText(context, "Please enter 10 digit mobile number", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    enabled = loginState !is UiState.Loading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                ) {
                    if (loginState is UiState.Loading) {
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