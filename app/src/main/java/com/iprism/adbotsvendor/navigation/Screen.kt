package com.iprism.adbotsvendor.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register/{mobile}") {
        fun createRoute(mobile : String) = "register/$mobile"
    }
    object Otp : Screen("otp")
    object Home : Screen("home")
    object Analytics : Screen("analytics")
    object Profile : Screen("profile")
    object PromotionDetails : Screen("promotion_details")
    object Notifications : Screen("notifications")
    object Wallet : Screen("wallet")
    object WalletHistory : Screen("wallet_history")
    object ContactUs : Screen("contact_us")
    object ContentPage : Screen("contentPage/{type}") {
        fun createRoute(type: String) = "contentPage/$type"
    }
    object BusinessDetails : Screen("business_details")
    object Preview : Screen("preview")
}