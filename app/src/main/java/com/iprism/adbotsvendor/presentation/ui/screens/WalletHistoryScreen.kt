package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.data.models.wallethistory.HistoryItem
import com.iprism.adbotsvendor.presentation.ui.components.LoadingScreen
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey2
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.viewmodels.WalletHistoryViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun WalletHistoryScreen(
    navController: NavHostController,
    viewModel: WalletHistoryViewModel = hiltViewModel()
) {
    val historyItems by viewModel.historyItems.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .statusBarsPadding()
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.back_img),
                    contentDescription = "Back",
                    tint = BLACK,
                    modifier = Modifier.size(28.dp)
                )
            }

            Text(
                text = "Wallet History",
                style = MaterialTheme.typography.headlineSmall,
                color = BLACK,
                modifier = Modifier.padding(all = 12.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(historyItems) { index, transaction ->
                    // Trigger pagination when reaching the end of the list
                    if (index >= historyItems.size - 1) {
                        viewModel.fetchWalletHistory()
                    }
                    TransactionItem(transaction)
                }
            }
        }

        if (uiState is UiState.Loading && historyItems.isEmpty()) {
            LoadingScreen()
        }

        if (uiState is UiState.Error && historyItems.isEmpty()) {
            Text(
                text = (uiState as UiState.Error).message,
                modifier = Modifier.align(Alignment.Center),
                color = Color.Red
            )
        }
    }
}

@Composable
fun TransactionItem(transaction: HistoryItem) {
    val amountColor = if (transaction.type.lowercase() == "debit") Color.Red else Color(0xFF00C566)
    val amountPrefix = if (transaction.type.lowercase() == "debit") "-₹" else "₹"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.transactionType,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = BLACK
            )
            Text(
                text = "$amountPrefix${transaction.amount}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = amountColor
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.description,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
            Text(
                text = transaction.createdOn,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
        }
    }
}