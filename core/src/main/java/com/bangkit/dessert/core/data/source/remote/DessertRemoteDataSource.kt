package com.bangkit.dessert.core.data.source.remote

import com.bangkit.dessert.core.data.source.remote.network.ApiResponse
import com.bangkit.dessert.core.data.source.remote.network.DessertApiService
import com.bangkit.dessert.core.data.source.remote.response.DessertBriefItemResponse
import com.bangkit.dessert.core.data.source.remote.response.DessertItemResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DessertRemoteDataSource @Inject constructor(private val apiService: DessertApiService) {

    suspend fun getAll(): ApiResponse<List<DessertBriefItemResponse>> {
        return try {
            val response = apiService.getAll()
            if (response.meals.isEmpty()) {
                ApiResponse.Empty
            } else {
                ApiResponse.Success(response.meals)
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }

    suspend fun getOne(id: Int): ApiResponse<DessertItemResponse> {
        return try {
            val response = apiService.getOne(id)
            val meals = response.meals
            if (meals.isNullOrEmpty()) {
                ApiResponse.Empty
            } else {
                ApiResponse.Success(meals.first())
            }
        } catch (e: Exception) {
            ApiResponse.Error(e)
        }
    }
}