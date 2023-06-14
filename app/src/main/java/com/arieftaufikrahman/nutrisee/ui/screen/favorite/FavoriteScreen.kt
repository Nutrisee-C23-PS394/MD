package com.arieftaufikrahman.nutrisee.ui.screen.favorite

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
fun FavoriteScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val favoriteViewModel = hiltViewModel<FavoriteViewModel>()

    favoriteViewModel.allFavoriteItem.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Error -> Error()
            is UiState.Success -> {
                FavoriteContent(
                    listFavoriteItem = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    onUpdateFavoriteItem = favoriteViewModel::updateFavoriteItem
                )
            }
        }
    }
}

@Composable
fun FavoriteContent(
    listFavoriteItem: List<ItemEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoriteItem: (id: Int, isFavorite: Boolean) -> Unit
) {
    when (listFavoriteItem.isEmpty()) {
        true -> EmptyContent()
        false -> AvailableContent(listFavoriteItem, navController, scaffoldState, onUpdateFavoriteItem)
    }
}