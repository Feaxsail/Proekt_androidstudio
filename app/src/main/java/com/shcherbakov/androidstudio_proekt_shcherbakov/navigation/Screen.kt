package com.shcherbakov.androidstudio_proekt_shcherbakov.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Details : Screen("details/{movieId}") {
        fun createRoute(movieId: String) = "details/$movieId"
    }
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}