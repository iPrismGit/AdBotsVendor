package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.presentation.ui.components.DottedDivider
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.Red
import com.iprism.adbotsvendor.presentation.ui.theme.White

@Composable
fun ProfileScreen(navController: NavHostController) {
    val gradientColors = listOf(Color(0xFF273F87), Color(0xFFEEEEEE), Color(0xFFEEEEEE))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(gradientColors))
            .padding(horizontal = 12.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.profile_img1),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "KV Ganesh",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.edit_icon),
                contentDescription = "Edit",
                tint = White,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ActionCard(
                modifier = Modifier.weight(1f),
                title = "Contact\nUs",
                icon = painterResource(
                    R.drawable.contact_us_img
                ),
                { navController.navigate("contact_us") }
            )
            ActionCard(
                modifier = Modifier.weight(1f),
                title = "Refer Your\nFriends",
                icon = painterResource(R.drawable.refer_img),
                navigate =
                    {}
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column {
                ProfileOptionItem(
                    icon = painterResource(R.drawable.profile_icon),
                    title = "Profile"
                )
                DottedDivider()
                ProfileOptionItem(
                    icon = painterResource(R.drawable.terms_img),
                    title = "Terms & Conditions"
                )
                DottedDivider()
                ProfileOptionItem(icon = painterResource(R.drawable.terms_img), title = "About Us")
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout Button
        Button(
            onClick = { /* Logout */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .border(0.dp, Red, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Red
            )
        ) {
            Text(
                text = "Logout",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun ActionCard(modifier: Modifier, title: String, icon: Painter, navigate: () -> Unit) {
    Card(
        modifier = modifier.fillMaxSize()
            .clickable(onClick = {navigate()}),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = BLACK
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontFamily = MontserratFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = BLACK
            )
        }
    }
}

@Composable
fun ProfileOptionItem(icon: Painter, title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Action */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = BLACK,
            modifier = Modifier.weight(1f)
        )
    }
}
