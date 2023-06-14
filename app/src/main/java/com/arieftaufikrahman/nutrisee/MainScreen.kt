package com.arieftaufikrahman.nutrisee

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.arieftaufikrahman.nutrisee.ui.navigation.NavigationItem
import com.arieftaufikrahman.nutrisee.ui.navigation.Screen
import com.arieftaufikrahman.nutrisee.ui.screen.daily.DailyScreen
import com.arieftaufikrahman.nutrisee.ui.screen.detail.DetailScreen
import com.arieftaufikrahman.nutrisee.ui.screen.favorite.FavoriteScreen
import com.arieftaufikrahman.nutrisee.ui.screen.home.HomeScreen
import com.arieftaufikrahman.nutrisee.ui.screen.setting.SettingScreen

@Composable
fun MainScreen(modifier: androidx.compose.ui.Modifier = androidx.compose.ui.Modifier) {
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailFood.route) {
                BottomBar(navController, currentRoute)
            }
        },
        scaffoldState = scaffoldState,
        snackbarHost = {
            SnackbarHost(hostState = it) { data ->
                Snackbar(snackbarData = data, shape = RoundedCornerShape(8.dp))
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(navController, scaffoldState)
            }
            composable(
                route = Screen.DetailFood.route,
                arguments = listOf(
                    navArgument("itemId") { type = NavType.IntType }
                )
            ) {
                val itemId = it.arguments?.getInt("itemId") ?: 0
                DetailScreen(itemId, navController, scaffoldState)
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(navController, scaffoldState)
            }
            composable(Screen.Daily.route) {
                DailyScreen(navController, scaffoldState)
            }
            composable(Screen.Settings.route) {
                SettingScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    currentRoute: String?,
) {
    val navigationItems = listOf(
        NavigationItem(
            title = "Home",
            icon = Icons.Rounded.Home,
            screen = Screen.Home,
            contentDescription = "home"
        ),
        NavigationItem(
            title = "Favorite",
            icon = Icons.Rounded.Favorite,
            screen = Screen.Favorite,
            contentDescription = "nav_favorite"
        ),
        NavigationItem(
            title = "Daily",
            icon = Icons.Rounded.List,
            screen = Screen.Daily,
            contentDescription = "nav_daily"
        ),
        NavigationItem(
            title = "Settings",
            icon = Icons.Rounded.Settings,
            screen = Screen.Settings,
            contentDescription = "nav_settings"
        ),
    )

    BottomNavigation(backgroundColor = Color.White) {
        navigationItems.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(item.title) },
                selected = currentRoute == item.screen.route,
                selectedContentColor = MaterialTheme.colors.primaryVariant,
                unselectedContentColor = Color.Gray,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}