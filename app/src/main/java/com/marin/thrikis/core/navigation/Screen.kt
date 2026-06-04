package com.marin.thrikis.core.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Menu : Screen("menu")
    object Game : Screen("game/{mode}") {
        fun createRoute(mode: String) = "game/$mode"
    }
    object Profile : Screen("profile/{userId}") {
        fun createRoute(userId: String) = "profile/$userId"
    }
    object Friends : Screen("friends")
}