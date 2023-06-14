package com.arieftaufikrahman.nutrisee.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val name: String,
    val image: String,
    val calories: Int,
    val gram: Int,

    val isFavorite: Boolean = false,
    val isOnCart: Boolean = false
)