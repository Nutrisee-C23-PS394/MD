package com.arieftaufikrahman.nutrisee.di

import android.app.Application
import androidx.room.Room
import com.arieftaufikrahman.nutrisee.data.local.ItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, ItemDatabase::class.java, "item.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: ItemDatabase) = database.itemDao()
}