package com.iprism.adbotsvendor.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.iprism.adbotsvendor.R
import com.iprism.adbotsvendor.navigation.Screen
import com.iprism.adbotsvendor.presentation.viewmodels.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavLogin : () -> Unit,
    onNavHome : () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isLoggedIn by viewModel.isLoggedIn.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        delay(500)
        if (isLoggedIn == true) {
            onNavHome()
        } else {
            onNavLogin()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(180.dp)
        )
    }
}
