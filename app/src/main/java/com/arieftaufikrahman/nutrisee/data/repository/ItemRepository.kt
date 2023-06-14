package com.arieftaufikrahman.nutrisee.data.repository

import com.arieftaufikrahman.nutrisee.data.local.ItemDao
import com.arieftaufikrahman.nutrisee.data.local.ItemEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ItemRepository @Inject constructor(private val itemDao: ItemDao) {
    fun getAllItem() = itemDao.getAllItem()
    fun getItem(id: Int) = itemDao.getItem(id)
    fun searchItem(query: String) = itemDao.searchItem(query)
    fun getAllFavoriteItem() = itemDao.getAllFavoriteItem()
    fun getAllCartItem() = itemDao.getAllCartItem()
    fun getRandomItem(limit: Int) = itemDao.getRandomItem(limit)
    suspend fun insertAllItem(item: List<ItemEntity>) = itemDao.insertAllItem(item)
    suspend fun updateFavoriteItem(id: Int, isFavorite: Boolean) = itemDao.updateFavoriteItem(id, isFavorite)
    suspend fun updateCartItem(id: Int, isOnCart: Boolean) = itemDao.updateCartItem(id, isOnCart)
}