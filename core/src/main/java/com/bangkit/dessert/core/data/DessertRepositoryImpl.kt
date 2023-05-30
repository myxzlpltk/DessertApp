package com.bangkit.dessert.core.data

import com.bangkit.dessert.core.data.source.local.DessertLocalDataSource
import com.bangkit.dessert.core.data.source.remote.DessertRemoteDataSource
import com.bangkit.dessert.core.data.source.remote.network.ApiResponse
import com.bangkit.dessert.core.data.source.remote.response.DessertBriefItemResponse
import com.bangkit.dessert.core.data.source.remote.response.DessertItemResponse
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.DessertBrief
import com.bangkit.dessert.core.domain.repository.DessertRepository
import com.bangkit.dessert.core.utils.DessertBriefMapper
import com.bangkit.dessert.core.utils.DessertMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DessertRepositoryImpl @Inject constructor(
    private val local: DessertLocalDataSource,
    private val remote: DessertRemoteDataSource,
) : DessertRepository {

    override fun getAll(): Flow<Resource<List<DessertBrief>>> = networkBoundResource(
        query = { local.getAll().map(DessertBriefMapper::fromLocal) },
        fetch = { remote.getAll() },
        saveFetchResult = { response -> saveAllToDatabase(response) },
        shouldFetch = { it.isEmpty() },
    ).flowOn(Dispatchers.IO)

    override suspend fun refresh() {
        val response = remote.getAll()
        saveAllToDatabase(response)
    }

    private suspend fun saveAllToDatabase(response: ApiResponse<List<DessertBriefItemResponse>>) {
        if (response is ApiResponse.Success) {
            val remoteList = response.data
            val domainList = DessertBriefMapper.fromRemote(remoteList)
            val localList = DessertBriefMapper.toLocal(domainList)
            local.insertAll(localList)
        }
    }

    override fun getOne(id: Int): Flow<Resource<Dessert?>> = networkBoundResource(
        query = { local.getOne(id).map { it?.let { DessertMapper.fromLocal(it) } } },
        fetch = { remote.getOne(id) },
        saveFetchResult = { response -> saveToDatabase(response) },
        shouldFetch = { it == null },
    ).flowOn(Dispatchers.IO)

    override suspend fun refreshOne(id: Int) {
        val response = remote.getOne(id)
        saveToDatabase(response)
    }

    private suspend fun saveToDatabase(response: ApiResponse<DessertItemResponse>) {
        if (response is ApiResponse.Success) {
            val remoteData = response.data
            val domainData = DessertMapper.fromRemote(remoteData)
            val localData = DessertMapper.toLocal(domainData, local.isFavorite(domainData.id))
            local.upsert(localData)
        }
    }

    override fun getFavorites(): Flow<Resource<List<DessertBrief>>> {
        return local.getFavorites()
            .map(DessertBriefMapper::fromLocal)
            .map { Resource.Success(it) }
    }

    override suspend fun setFavorite(id: Int, isFavorite: Boolean) {
        local.setFavorite(id, isFavorite)
    }
}