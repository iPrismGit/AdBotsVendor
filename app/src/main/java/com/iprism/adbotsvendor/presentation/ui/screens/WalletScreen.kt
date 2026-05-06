package com.iprism.adbotsvendor.presentation.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.viewmodels.WalletViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun WalletScreen(
    onBack: () -> Unit,
    onNavigateWalletHistory: () -> Unit,
    viewModel: WalletViewModel = hiltViewModel()
) {

    var amount by rememberSaveable { mutableStateOf("") }
    val selectedAmount = rememberSaveable { mutableIntStateOf(0) }
    val state by viewModel.response.collectAsStateWithLifecycle()
    var walletAmount by remember { mutableStateOf("0") }
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is UiState.Success) {
            walletAmount = (state as UiState.Success).data.response.wallet ?: "0"
            amount = ""
            selectedAmount.intValue = 0
        }
    }

    LaunchedEffect(Unit) {
        viewModel.event.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBack() },
                modifier = Modifier.padding(start = 6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_img),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(
                onClick = { onNavigateWalletHistory() },
                modifier = Modifier.padding(end = 6.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.wallet_history_img),
                    contentDescription = "History",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Wallet",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "₹$walletAmount",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = MontserratFamily,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Available Wallet Amount",
                    fontSize = 21.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = MontserratFamily,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        amount = it
                    }
                },
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                placeholder = {
                    Text(
                        "Add money to Wallet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(30.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                singleLine = true,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "You can also choose",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MontserratFamily,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                val options = listOf(100, 1000, 2000)
                options.forEach { opt ->
                    val isSelected = selectedAmount.intValue == opt
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(45.dp)
                            .clip(RoundedCornerShape(25.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(25.dp)
                            )
                            .clickable {
                                selectedAmount.intValue = opt
                                amount = opt.toString()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "₹$opt",
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Normal,
                            fontFamily = MontserratFamily,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        Button(
            onClick = {
                viewModel.wallet("12345", amount.toIntOrNull() ?: 0, "recharge_wallet")
                amount = ""
                selectedAmount.intValue = 0
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
                .imePadding(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            enabled = amount.toIntOrNull() != null && amount.isNotEmpty()
        ) {
            Text(
                text = "Continue",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
    if (state is UiState.Loading) {
        LoadingScreen()
    }
}
