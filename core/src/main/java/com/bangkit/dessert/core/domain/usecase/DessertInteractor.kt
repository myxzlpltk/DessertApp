package com.bangkit.dessert.core.domain.usecase

import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.domain.repository.DessertRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DessertInteractor @Inject constructor(
    private val dessertRepository: DessertRepository
) : DessertUseCase {

    override suspend fun refresh() {
        dessertRepository.refresh()
    }

    override fun getAll(): Flow<Resource<List<DessertBrief>>> {
        return dessertRepository.getAll()
    }

    override suspend fun refreshOne(id: Int) {
        dessertRepository.refreshOne(id)
    }

    override fun getOne(id: Int): Flow<Resource<Dessert?>> {
        return dessertRepository.getOne(id)
    }

    override fun getFavorites(): Flow<Resource<List<DessertBrief>>> {
        return dessertRepository.getFavorites()
    }

    override suspend fun setFavorite(id: Int, isFavorite: Boolean) {
        dessertRepository.setFavorite(id, isFavorite)
    }
}