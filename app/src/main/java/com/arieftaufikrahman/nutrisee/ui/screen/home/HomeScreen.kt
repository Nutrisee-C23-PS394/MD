package com.arieftaufikrahman.nutrisee.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
import com.arieftaufikrahman.nutrisee.ui.common.UiState
import com.arieftaufikrahman.nutrisee.ui.component.*

@Composable
fun HomeScreen(navController: NavController, scaffoldState: ScaffoldState) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val homeState by homeViewModel.homeState

    homeViewModel.allItem.collectAsState(UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> LoadingState()
            is UiState.Error -> ErrorContent()
            is UiState.Success -> {
                HomeContent(
                    listItem = uiState.data,
                    navController = navController,
                    scaffoldState = scaffoldState,
                    query = homeState.query,
                    onQueryChange = homeViewModel::onQueryChange,
                    onUpdateFavoriteItem = homeViewModel::updateFavoriteItem
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    listItem: List<ItemEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    query: String,
    onQueryChange: (String) -> Unit,
    onUpdateFavoriteItem: (id: Int, isFavorite: Boolean) -> Unit
) {
    Column {
        SearchBar(query = query, onQueryChange = onQueryChange)
        when (listItem.isEmpty()) {
            true -> EmptyContent()
            false -> AvailableContent(listItem, navController, scaffoldState, onUpdateFavoriteItem)
        }
    }
}

