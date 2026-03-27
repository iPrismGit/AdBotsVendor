package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey2
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily

@Composable
fun WalletHistoryScreen(navController: NavHostController) {
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
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            val transactions = listOf(
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "27-Oct-2023", Color.Red),
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "26-Oct-2023", Color.Red),
                TransactionData("Wallet Recharged", "Recharge Successful", "₹1000", "27-Oct-2023", Color(0xFF00C566)),
                TransactionData("Wallet Refund", "Order Placed", "₹44.00", "26-Oct-2023", Color(0xFF00C566)),
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "27-Oct-2023", Color.Red),
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "26-Oct-2023", Color.Red),
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "27-Oct-2023", Color.Red),
                TransactionData("Wallet Debited", "Order Placed", "₹44.00", "26-Oct-2023", Color.Red),
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "26-Oct-2023", Color.Red),
                TransactionData("Wallet Debited", "Order Placed", "-₹44.00", "27-Oct-2023", Color.Red)
            )

            items(transactions) { transaction ->
                TransactionItem(transaction)
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: TransactionData) {
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
                text = transaction.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = BLACK
            )
            Text(
                text = transaction.amount,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = transaction.amountColor
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = transaction.subtitle,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
            Text(
                text = transaction.date,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
        }
    }
}

data class TransactionData(
    val title: String,
    val subtitle: String,
    val amount: String,
    val date: String,
    val amountColor: Color
)
