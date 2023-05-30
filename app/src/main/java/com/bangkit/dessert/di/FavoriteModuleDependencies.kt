package com.bangkit.dessert.di

import com.bangkit.dessert.core.domain.usecase.DessertUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun provideUseCase(): DessertUseCase
}