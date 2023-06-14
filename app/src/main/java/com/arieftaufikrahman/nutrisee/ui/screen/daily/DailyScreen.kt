package com.arieftaufikrahman.nutrisee.ui.screen.daily

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
import com.arieftaufikrahman.nutrisee.ui.common.UiState
import com.arieftaufikrahman.nutrisee.ui.component.AvailableContent
import com.arieftaufikrahman.nutrisee.ui.component.EmptyContent
import com.arieftaufikrahman.nutrisee.ui.component.LoadingState

@Composable
fun DailyScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val dailyViewModel = hiltViewModel<DailyViewModel>()

    dailyViewModel.allDailyItem.collectAsState(UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Error -> Error()
            is UiState.Success -> {
                DailyContent(
                    listDailyItem = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateToCart = dailyViewModel::updateDailyItem
                )
            }
        }
    }
}

@Composable
fun DailyContent(
    listDailyItem: List<ItemEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateToCart: (id: Int, isOnCart: Boolean) -> Unit
) {
    when(listDailyItem.isEmpty()) {
        true -> EmptyContent()
        false -> AvailableContent(listDailyItem, navController, scaffoldState,  onUpdateToCart)
    }
}