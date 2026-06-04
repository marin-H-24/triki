package com.marin.thrikis.core.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.marin.thrikis.ui.game.GameScreen
import com.marin.thrikis.ui.game.GameViewModel
import com.marin.thrikis.ui.menu.MenuScreen
import com.marin.thrikis.ui.menu.MenuViewModel
import com.marin.thrikis.ui.profile.ProfileScreen
import com.marin.thrikis.ui.profile.ProfileViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Menu.route
    ) {
        composable(Screen.Login.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Pantalla de Login (Próximamente)")
            }
        }

        composable(Screen.Menu.route) {
            val menuViewModel: MenuViewModel = viewModel()
            MenuScreen(
                viewModel = menuViewModel,
                onNavigateToGame = { mode ->
                    navController.navigate(Screen.Game.createRoute(mode))
                },
                onNavigateToProfile = { userId ->
                    navController.navigate(Screen.Profile.createRoute(userId))
                },
                onNavigateToFriends = {
                    navController.navigate(Screen.Friends.route)
                }
            )
        }

        composable(Screen.Game.route) { backStackEntry ->
            val mode = backStackEntry.arguments?.getString("mode") ?: "local"
            val gameViewModel: GameViewModel = viewModel()
            GameScreen(
                mode = mode,
                viewModel = gameViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Profile.route) {
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Friends.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Pantalla de Amigos (Próximamente)")
            }
        }
    }
}