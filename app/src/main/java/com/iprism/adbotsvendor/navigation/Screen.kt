package com.iprism.adbotsvendor.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Otp : Screen("otp")
    object Home : Screen("home")
}