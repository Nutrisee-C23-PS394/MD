package com.arieftaufikrahman.nutrisee.ui.screen.detail

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
class DetailViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel() {
    private val _item = MutableStateFlow<UiState<ItemEntity>>(UiState.Loading)
    val item = _item.asStateFlow()

    fun getItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getItem(id)
                .catch { _item.value = UiState.Error(it.message.toString()) }
                .collect { _item.value = UiState.Success(it) }
        }
    }

    fun updateFavoriteItem(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavoriteItem(id, isFavorite)
        }
    }

    fun updateDailyItem(id: Int, isOnCart: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartItem(id, isOnCart)
        }
    }
}