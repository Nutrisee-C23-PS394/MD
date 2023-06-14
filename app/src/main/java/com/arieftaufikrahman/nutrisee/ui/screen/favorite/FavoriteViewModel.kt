package com.arieftaufikrahman.nutrisee.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
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
class FavoriteViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    private val _allFavoriteItem = MutableStateFlow<UiState<List<ItemEntity>>>(UiState.Loading)
    val allFavoriteItem = _allFavoriteItem.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllFavoriteItem()
                .catch { _allFavoriteItem.value = UiState.Error(it.message.toString()) }
                .collect { _allFavoriteItem.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteItem(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteItem(id, isFavorite)
        }
    }
}