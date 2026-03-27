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
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK1
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey1
import com.iprism.adbotsvendor.presentation.ui.theme.LightGrey2
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily

@Composable
fun NotificationsScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
    ) {
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier.padding(start = 6.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.back_img),
                contentDescription = "Back",
                tint = BLACK,
                modifier = Modifier.size(28.dp)
            )
        }

        Text(
            text = "Notifications",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.Black,
            modifier = Modifier.padding(12.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            val dummyNotifications = listOf(
                NotificationData(
                    "Lorem Ipsum",
                    "Lorem Lorem ipsum dummy text",
                    "Monday 06:21"
                ),
                NotificationData(
                    "Lorem Ipsum",
                    "Many desktop publishing packages and web page editors now use Lorem",
                    "Monday 06:21"
                ),
                NotificationData(
                    "Lorem Ipsum",
                    "Many desktop publishing packages and web page editors now use Lorem",
                    "Monday 06:21"
                ),
                NotificationData(
                    "Lorem Ipsum",
                    "Many desktop publishing packages and web page editors now use Lorem",
                    "Monday 06:21"
                )
            )

            items(dummyNotifications) { notification ->
                NotificationItem(notification)
                HorizontalDivider(thickness = 1.dp, color = LightGrey1)
            }
        }
    }
}

@Composable
fun NotificationItem(notification: NotificationData) {
    Column(
        modifier = Modifier
            .fillMaxWidth().padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = notification.title,
                fontSize = 14.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.SemiBold,
                color = BLACK1
            )
            Text(
                text = notification.time,
                fontSize = 12.sp,
                fontFamily = MontserratFamily,
                fontWeight = FontWeight.Normal,
                color = LightGrey2
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = notification.body,
            fontSize = 14.sp,
            color = LightGrey2,
            fontFamily = MontserratFamily,
            fontWeight = FontWeight.Normal,
        )
    }
}

data class NotificationData(
    val title: String,
    val body: String,
    val time: String
)
