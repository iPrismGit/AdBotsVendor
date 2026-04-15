package com.iprism.adbotsvendor.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iprism.adbotsvendor.presentation.ui.components.BottomNavigationBar
import com.iprism.adbotsvendor.presentation.ui.screens.AnalyticsScreen
import com.iprism.adbotsvendor.presentation.ui.screens.BusinessDetailsScreen
import com.iprism.adbotsvendor.presentation.ui.screens.ContactUsScreen
import com.iprism.adbotsvendor.presentation.ui.screens.HomeScreen
import com.iprism.adbotsvendor.presentation.ui.screens.LoginScreen
import com.iprism.adbotsvendor.presentation.ui.screens.NotificationsScreen
import com.iprism.adbotsvendor.presentation.ui.screens.OtpScreen
import com.iprism.adbotsvendor.presentation.ui.screens.PreviewScreen
import com.iprism.adbotsvendor.presentation.ui.screens.ProfileScreen
import com.iprism.adbotsvendor.presentation.ui.screens.PromotionDetailsScreen
import com.iprism.adbotsvendor.presentation.ui.screens.RegisterScreen
import com.iprism.adbotsvendor.presentation.ui.screens.SplashScreen
import com.iprism.adbotsvendor.presentation.ui.screens.ContentPageScreen
import com.iprism.adbotsvendor.presentation.ui.screens.WalletHistoryScreen
import com.iprism.adbotsvendor.presentation.ui.screens.WalletScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf(
        Screen.Home.route,
        Screen.Analytics.route,
        Screen.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = Modifier.padding(bottom = if (showBottomBar) innerPadding.calculateBottomPadding() else 0.dp)
        ) {
            composable(Screen.Splash.route) {
                SplashScreen(navController)
            }
            composable(Screen.Login.route) {
                LoginScreen(onNavigateToOtp = { otp, mobile ->
                    navController.navigate("${Screen.Otp.route}/$otp/$mobile")
                })
            }
            composable("${Screen.Otp.route}/{otp}/{mobile}") { backStackEntry ->
                val otp = backStackEntry.arguments?.getString("otp") ?: ""
                val mobile = backStackEntry.arguments?.getString("mobile") ?: ""
                OtpScreen(
                    otp = otp,
                    mobile = mobile,
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigateToRegister = {
                        navController.navigate(Screen.Register.createRoute(mobile))
                    },
                    onNavigateToHome = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(navController)
            }
            composable(Screen.Analytics.route) {
                AnalyticsScreen(navController)
            }
            composable(Screen.PromotionDetails.route) {
                PromotionDetailsScreen(navController)
            }
            composable(Screen.Profile.route) {
                ProfileScreen(navController)
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen(navController)
            }
            composable(Screen.Wallet.route) {
                WalletScreen({navController.popBackStack()},{navController.navigate(Screen.WalletHistory.route)})
            }
            composable(Screen.WalletHistory.route) {
                WalletHistoryScreen(navController)
            }
            composable(Screen.ContactUs.route) {
                ContactUsScreen({ navController.popBackStack() }, { navController.popBackStack() })
            }
            composable(Screen.ContentPage.route) { backStackEntry ->
                val type = backStackEntry.arguments?.getString("type") ?: ""
                ContentPageScreen(
                    type = type,
                    onBack = { navController.popBackStack() }
                )
            }
            composable(Screen.BusinessDetails.route) {
                BusinessDetailsScreen(
                    { navController.popBackStack() },
                    { navController.navigate(Screen.Preview.route) })
            }
            composable(Screen.Preview.route) {
                PreviewScreen(navController)
            }
            composable(Screen.Register.route) { backStackEntry ->
                val mobile = backStackEntry.arguments?.getString("mobile") ?: ""
                RegisterScreen(
                    mobile,
                    { navController.popBackStack() },
                    {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    })
            }
        }
    }
}
