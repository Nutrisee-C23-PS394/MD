package com.arieftaufikrahman.nutrisee.di

import com.arieftaufikrahman.nutrisee.data.local.ItemDao
import com.arieftaufikrahman.nutrisee.data.repository.ItemRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideRepository(itemDao: ItemDao) = ItemRepository(itemDao)
}