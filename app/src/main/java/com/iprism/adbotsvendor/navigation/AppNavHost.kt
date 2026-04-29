package com.iprism.adbotsvendor.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.iprism.adbotsvendor.presentation.viewmodels.AddPromotionViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.iprism.adbotsvendor.presentation.ui.components.BottomNavigationBar
import com.iprism.adbotsvendor.presentation.ui.screens.AnalyticsScreen
import com.iprism.adbotsvendor.presentation.ui.screens.PromotionScreen
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
import com.iprism.adbotsvendor.presentation.ui.screens.EditProfileScreen
import com.iprism.adbotsvendor.presentation.ui.screens.WalletHistoryScreen
import com.iprism.adbotsvendor.presentation.ui.screens.WalletScreen
import com.iprism.adbotsvendor.presentation.viewmodels.AnalyticsViewModel
import com.iprism.adbotsvendor.presentation.viewmodels.HomeViewModel
import com.iprism.adbotsvendor.presentation.viewmodels.ProfileViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar by remember(currentRoute) {
        derivedStateOf {
            currentRoute in listOf(
                Screen.Home.route,
                Screen.Analytics.route,
                Screen.Profile.route
            )
        }
    }

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
                SplashScreen({
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }, {
                    navController.navigate("main") {   // 🔥 IMPORTANT
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                })
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
                        navController.navigate("main") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    },
                )
            }
            navigation(route = "main", startDestination = Screen.Home.route) {
                composable(Screen.Home.route) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("main")
                    }
                    val homeViewModel: HomeViewModel = hiltViewModel(parentEntry)
                    HomeScreen(
                        { navController.navigate(Screen.Wallet.route) },
                        { navController.navigate(Screen.Notifications.route) },
                        { navController.navigate(Screen.Analytics.route) },
                        { navController.navigate(Screen.BusinessDetails.route) },
                        homeViewModel = homeViewModel
                    )
                }

                composable(Screen.Analytics.route) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("main")
                    }
                    val viewModel: AnalyticsViewModel = hiltViewModel(parentEntry)
                    AnalyticsScreen(
                        { navController.navigate(Screen.Wallet.route) },
                        { navController.navigate(Screen.Notifications.route) },
                        { id -> navController.navigate(Screen.PromotionDetails.createRoute(id)) },
                        viewModel = viewModel
                    )
                }

                composable(Screen.Profile.route) { backStackEntry ->
                    val parentEntry = remember(backStackEntry) {
                        navController.getBackStackEntry("main")
                    }
                    val viewModel: ProfileViewModel = hiltViewModel(parentEntry)
                    ProfileScreen(navController, viewModel)
                }
            }

            composable(Screen.PromotionDetails.route) {
                PromotionDetailsScreen({ navController.popBackStack() })
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen(navController)
            }
            composable(Screen.Wallet.route) {
                WalletScreen(
                    { navController.popBackStack() },
                    { navController.navigate(Screen.WalletHistory.route) })
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
                val viewModel: AddPromotionViewModel = hiltViewModel()
                PromotionScreen(
                    onBack = { navController.popBackStack() },
                    onContinueClick = { navController.navigate(Screen.Preview.route) },
                    viewModel = viewModel
                )
            }
            composable(Screen.Preview.route) { backStackEntry ->
                val parentEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Screen.BusinessDetails.route)
                }
                val viewModel: AddPromotionViewModel = hiltViewModel(parentEntry)
                PreviewScreen(navController, viewModel)
            }
            composable(Screen.Register.route) { backStackEntry ->
                val mobile = backStackEntry.arguments?.getString("mobile") ?: ""
                RegisterScreen(
                    mobile,
                    { navController.popBackStack() },
                    {
                        navController.navigate("main") {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    })
            }
            composable(Screen.EditProfile.route) {
                EditProfileScreen(
                    { navController.popBackStack() },
                )
            }
        }
    }
}
