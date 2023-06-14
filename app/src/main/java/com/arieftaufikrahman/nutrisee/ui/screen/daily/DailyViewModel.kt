package com.arieftaufikrahman.nutrisee.ui.screen.daily

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
class DailyViewModel @Inject constructor(private val repository: ItemRepository) : ViewModel(){
    private val _allDailyItem = MutableStateFlow<UiState<List<ItemEntity>>>(UiState.Loading)
    val allDailyItem = _allDailyItem.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllCartItem()
                .catch { _allDailyItem.value = UiState.Error(it.message.toString()) }
                .collect { _allDailyItem.value = UiState.Success(it) }
        }
    }

    fun updateDailyItem(id: Int, isOnCart: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCartItem(id, isOnCart)
        }
    }
}