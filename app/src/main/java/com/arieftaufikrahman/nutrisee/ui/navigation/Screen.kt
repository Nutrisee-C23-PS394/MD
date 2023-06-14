package com.arieftaufikrahman.nutrisee.ui.navigation

sealed class Screen(val route: String) {

    object Home : Screen("home")
    object Favorite: Screen("favorite")
    object Daily: Screen("daily")
    object Settings: Screen("setting")
    object DetailFood: Screen("home/{itemId}") {
        fun createRoute(itemId: Int) = "home/$itemId"
    }
}