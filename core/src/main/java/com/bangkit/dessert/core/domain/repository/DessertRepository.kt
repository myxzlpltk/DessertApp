package com.bangkit.dessert.core.domain.repository

import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import kotlinx.coroutines.flow.Flow

interface DessertRepository {

    // List dessert
    fun getAll(): Flow<Resource<List<DessertBrief>>>
    suspend fun refresh()

    // Single dessert
    fun getOne(id: Int): Flow<Resource<Dessert?>>
    suspend fun refreshOne(id: Int)

    // Favorites
    fun getFavorites(): Flow<Resource<List<DessertBrief>>>
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}