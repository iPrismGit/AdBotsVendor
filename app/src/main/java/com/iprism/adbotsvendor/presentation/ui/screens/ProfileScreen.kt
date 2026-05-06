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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.navigation.Screen
import com.iprism.adbotsvendor.presentation.ui.components.DottedDivider
import com.iprism.adbotsvendor.presentation.ui.theme.BLACK
import com.iprism.adbotsvendor.presentation.ui.theme.MontserratFamily
import com.iprism.adbotsvendor.presentation.ui.theme.Red
import com.iprism.adbotsvendor.presentation.ui.theme.White

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iprism.adbotsvendor.presentation.viewmodels.ProfileViewModel
import com.iprism.adbotsvendor.utils.UiState

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel
) {
    val gradientColors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.background)
    var showLogoutDialog by remember { mutableStateOf(false) }

    val profileState by viewModel.profileState.collectAsStateWithLifecycle()
    val userName = remember(profileState) {
        if (profileState is UiState.Success) {
            (profileState as UiState.Success).data.response.name
        } else {
            "Loading..."
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchProfile()
        viewModel.eventFlow.collect { event ->
            if (event is ProfileViewModel.UiEvent.Logout) {
                navController.navigate(Screen.Login.route) {
                    popUpTo(0) { inclusive = true } }
            }
        }
    }

    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = {
                showLogoutDialog = false
                viewModel.logout()
            }
        )
    }

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
                text = userName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {navController.navigate("edit_profile")}) {
                Icon(
                    painter = painterResource(R.drawable.edit_icon),
                    contentDescription = "Edit",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(32.dp)
                )
            }
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
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column {
                ProfileOptionItem(
                    icon = painterResource(R.drawable.profile_icon),
                    title = "Profile",
                    navigate = { navController.navigate("edit_profile") }
                )
                DottedDivider()
                ProfileOptionItem(
                    icon = painterResource(R.drawable.terms_img),
                    title = "Terms & Conditions",
                    navigate = { navController.navigate(Screen.ContentPage.createRoute("terms")) }
                )
                DottedDivider()
                ProfileOptionItem(
                    icon = painterResource(R.drawable.terms_img),
                    title = "About Us",
                    navigate = { navController.navigate(Screen.ContentPage.createRoute("about_us")) }
                )
                DottedDivider()
                ProfileOptionItem(
                    icon = painterResource(R.drawable.terms_img),
                    title = "Privacy Policy",
                    navigate = { navController.navigate(Screen.ContentPage.createRoute("privacy")) }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // Logout Button
        Button(
            onClick = { showLogoutDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp)
                .border(0.dp, MaterialTheme.colorScheme.error, RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.error
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
            containerColor = MaterialTheme.colorScheme.surfaceVariant
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
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontFamily = MontserratFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ProfileOptionItem(icon: Painter, title: String, navigate: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigate() }
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
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Are you sure you want\nLogout?",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = onConfirm,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.onSurface)
                    ) {
                        Text(text = "Yes", color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp, fontFamily = MontserratFamily
                            , fontWeight = FontWeight.Normal)
                    }

                    Button(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2CC990))
                    ) {
                        Text(text = "No", fontSize = 16.sp, color = Color.White, fontFamily = MontserratFamily, fontWeight = FontWeight.Normal)
                    }
                }
            }
        }
    }
}