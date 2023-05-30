package com.bangkit.dessert.core.domain.usecase

import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import kotlinx.coroutines.flow.Flow

interface DessertUseCase {
    suspend fun refresh()
    fun getAll(): Flow<Resource<List<DessertBrief>>>

    suspend fun refreshOne(id: Int)
    fun getOne(id: Int): Flow<Resource<Dessert?>>

    fun getFavorites(): Flow<Resource<List<DessertBrief>>>
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}