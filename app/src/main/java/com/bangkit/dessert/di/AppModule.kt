package com.bangkit.dessert.di

import com.bangkit.dessert.core.domain.usecase.DessertInteractor
import com.bangkit.dessert.core.domain.usecase.DessertUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun provideUseCase(dessertInteractor: DessertInteractor): DessertUseCase
}