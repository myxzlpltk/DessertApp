package com.bangkit.dessert.core.di

import com.bangkit.dessert.core.data.DessertRepositoryImpl
import com.bangkit.dessert.core.domain.repository.DessertRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(dessertRepository: DessertRepositoryImpl): DessertRepository
}