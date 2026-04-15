package com.shcherbakov.androidstudio_proekt_shcherbakov.navigation


import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.shcherbakov.androidstudio_proekt_shcherbakov.uii.DetailsScreen
import com.shcherbakov.androidstudio_proekt_shcherbakov.uii.HomeScreen
import com.shcherbakov.androidstudio_proekt_shcherbakov.uii.ProfileScreen
import com.shcherbakov.androidstudio_proekt_shcherbakov.uii.SettingsScreen
import com.shcherbakov.androidstudio_proekt_shcherbakov.viewmodel.MovieViewModel

@Composable
fun MovieNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // 1. Главный экран (список фильмов)
        composable(route = Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                onProfileClick = {
                    navController.navigate(Screen.Profile.route)
                },
                onSettingsClick = {
                    navController.navigate(Screen.Settings.route)
                }
            )
        }

        // 2. Экран деталей (с параметром movieId + ViewModel)
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val viewModel: MovieViewModel = viewModel() // Создаём ViewModel

            DetailsScreen(
                movieId = movieId,
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel // Передаём в экран
            )
        }

        // 3. Экран профиля
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        // 4. Экран настроек
        composable(route = Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}