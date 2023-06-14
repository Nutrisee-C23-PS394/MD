package com.arieftaufikrahman.nutrisee.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao{

    @Query("SELECT * FROM item")
    fun getAllItem(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item WHERE isFavorite = 1")
    fun getAllFavoriteItem(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item where isOnCart = 1")
    fun getAllCartItem(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item WHERE id = :id")
    fun getItem(id: Int): Flow<ItemEntity>

    @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%'")
    fun searchItem(query: String): Flow<List<ItemEntity>>

    @Query("SELECT * FROM item ORDER BY RANDOM() LIMIT :limit")
    fun getRandomItem(limit: Int): Flow<List<ItemEntity>>

    @Query("UPDATE item SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteItem(id: Int, isFavorite: Boolean)

    @Query("UPDATE item SET isOnCart = :isOnCart WHERE id = :id")
    suspend fun updateCartItem(id: Int, isOnCart: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItem(itemList: List<ItemEntity>)
}