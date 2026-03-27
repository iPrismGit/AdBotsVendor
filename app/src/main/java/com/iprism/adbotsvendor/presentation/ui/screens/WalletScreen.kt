package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.BorderGrey
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.Grey555
import com.iprism.adbotsvendor.presentation.ui.theme.LightBlue
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White

@Composable
fun WalletScreen(navController: NavHostController) {
    var amount by remember { mutableStateOf("") }
    val selectedAmount = remember { mutableIntStateOf(100) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.padding(start = 6.dp)) {
                Icon(
                    painter = painterResource(R.drawable.back_img),
                    contentDescription = "Back",
                    modifier = Modifier.size(28.dp)
                )
            }
            IconButton(onClick = { }, modifier = Modifier.padding(end = 6.dp)) {
                Icon(
                    painter = painterResource(R.drawable.wallet_history_img),
                    contentDescription = "History",
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Wallet",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "₹0",
                fontSize = 30.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = MontserratFamily,
                color = BLACK
            )
            Text(
                text = "Available Wallet Amount",
                fontSize = 21.sp,
                color = BLACK,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            textStyle = MaterialTheme.typography.bodySmall,
            placeholder = { 
                Text(
                    "Add money to Wallet", 
                    color = Grey555,
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
                unfocusedBorderColor = BorderGrey,
                focusedBorderColor = LightBlue
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "You can also choose",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = MontserratFamily,
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
                        .background(if (isSelected) LightBlue else Color.White)
                        .border(
                            width = 1.dp,
                            color = if (isSelected) LightBlue else BorderGrey,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .clickable { selectedAmount.intValue = opt },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "₹$opt",
                        color = if (isSelected) Color.White else Color.LightGray,
                        fontWeight = FontWeight.Normal,
                        fontFamily = MontserratFamily,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { /* Continue logic */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp),
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
        
        Spacer(modifier = Modifier.height(12.dp))
    }
}
