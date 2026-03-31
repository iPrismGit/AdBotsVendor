package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.CustomTextField
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.DarkBlue
import com.iprism.adbotsvendor.presentation.ui.theme.LightBlue2
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey1
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.White

@Composable
fun PreviewScreen(navController: NavHostController) {
    var isWalletUsed by remember { mutableStateOf(false) }
    var isTermsAccepted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .statusBarsPadding()
    ) {
        // Back Button
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(top = 8.dp, start = 8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.back_img),
                contentDescription = "Back",
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Preview",
                style = MaterialTheme.typography.headlineSmall,
                color = BLACK
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Charges Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Brush.horizontalGradient(listOf(Color(0xFF015DC5), Color(0xFF357ABD))))
                    .padding(16.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Charges", color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = MontserratFamily)
                        Text("₹1000", color = White, fontSize = 14.sp, fontWeight = FontWeight.Bold, fontFamily = MontserratFamily)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Start Date : 21-03-2026", color = White, fontSize = 12.sp, fontFamily = MontserratFamily, fontWeight = FontWeight.Light)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("End Date : 21-04-2026", color = White, fontSize = 12.sp, fontFamily = MontserratFamily, fontWeight = FontWeight.Light)
                }
                
                Button(
                    onClick = { /* Change Date */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(32.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3B30))
                ) {
                    Text("Change Date", fontSize = 10.sp, fontWeight = FontWeight.Medium, fontFamily = MontserratFamily, color = White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Promotional Video", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = BLACK, fontFamily = MontserratFamily)
            Spacer(modifier = Modifier.height(12.dp))

            // File Selector Placeholder
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(8.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id =R.drawable.cross_img),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("File6353737.MP4", modifier = Modifier.weight(1f), fontSize = 12.sp, color = BLACK1, fontFamily = MontserratFamily, fontWeight = FontWeight.Normal)
                Text(
                    "Preview",
                    color = LightBlue2,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = MontserratFamily,
                    modifier = Modifier.clickable { /* Preview video */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Wallet Amount Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.wallet_img1),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Wallet Amount", fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = MontserratFamily, color = BLACK)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Use Wallet Funds", fontSize = 10.sp, color = BLACK, fontFamily = MontserratFamily, fontWeight = FontWeight.Normal)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("₹1000", fontWeight = FontWeight.Normal, fontSize = 14.sp, fontFamily = MontserratFamily, color = BLACK)
                    }
                    Checkbox(
                        checked = isWalletUsed,
                        onCheckedChange = { isWalletUsed = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Contact Details", fontWeight = FontWeight.Bold, fontSize = 14.sp, fontFamily = MontserratFamily, color = BLACK)
            
            Spacer(modifier = Modifier.height(16.dp))
            Text("Name", style = MaterialTheme.typography.bodySmall, color = BLACK1)
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField("Ganesh", "", onValueChange = {})
            Spacer(modifier = Modifier.height(12.dp))
            Text("Mobile Number", style = MaterialTheme.typography.bodySmall, color = BLACK1)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = "+91",
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodySmall,
                    shape = RoundedCornerShape(8.dp),   // 🔥 rounded corners
                    modifier = Modifier.width(70.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LightGrey1,
                        unfocusedBorderColor = LightGrey1,
                        disabledBorderColor = LightGrey1
                    )
                )
                OutlinedTextField(
                    value = "89375447487",
                    onValueChange = {},
                    readOnly = true,
                    textStyle = MaterialTheme.typography.bodySmall,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LightGrey1,
                        unfocusedBorderColor = LightGrey1,
                        disabledBorderColor = LightGrey1
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))
            
            // Terms Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Checkbox(
                    checked = isTermsAccepted,
                    onCheckedChange = { isTermsAccepted = it },
                    modifier = Modifier.offset(y = (-12).dp)
                )
                Text(
                    text = "By clicking continue, I accept the terms of service & privacy policy",
                    fontSize = 12.sp,
                    fontFamily = MontserratFamily,
                    fontWeight = FontWeight.Normal,
                    color = LightBlue2,
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }

        // Continue Button
        Button(
            onClick = { /* Handle Continue */ },
            modifier = Modifier
                .fillMaxWidth().padding(12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text("Continue", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(8.dp))
        }
    }
}