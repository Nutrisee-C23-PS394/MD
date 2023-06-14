package com.arieftaufikrahman.nutrisee.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
import com.arieftaufikrahman.nutrisee.data.model.ItemData
import com.arieftaufikrahman.nutrisee.data.repository.ItemRepository
import com.arieftaufikrahman.nutrisee.ui.common.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    private val _allItem = MutableStateFlow<UiState<List<ItemEntity>>>(UiState.Loading)
    val allItem = _allItem.asStateFlow()

    private val _homeState = mutableStateOf(HomeState())
    val homeState: State<HomeState> = _homeState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllItem().collect { item ->
                when (item.isEmpty()) {
                    true -> repository.insertAllItem(ItemData.dummyItem)
                    else -> _allItem.value = UiState.Success(item)
                }
            }
        }
    }

    private fun searchItem(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.searchItem(query)
                .catch { _allItem.value = UiState.Error(it.message.toString()) }
                .collect { _allItem.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteItem(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteItem(id, isFavorite)
        }
    }

    fun onQueryChange(query: String) {
        _homeState.value = _homeState.value.copy(query = query)
        searchItem(query)
    }
}