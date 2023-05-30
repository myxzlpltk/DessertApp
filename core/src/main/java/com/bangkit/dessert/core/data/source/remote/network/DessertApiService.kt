package com.bangkit.dessert.core.data.source.remote.network

import com.bangkit.dessert.core.data.source.remote.response.DessertResponse
import com.bangkit.dessert.core.data.source.remote.response.ListDessertResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DessertApiService {

    @GET("filter.php")
    suspend fun getAll(
        @Query("c") category: String = "Dessert"
    ): ListDessertResponse

    @GET("lookup.php")
    suspend fun getOne(
        @Query("i") id: Int
    ): DessertResponse
}