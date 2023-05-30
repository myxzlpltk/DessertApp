package com.bangkit.dessert.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.bangkit.dessert.core.data.source.local.entity.DessertEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DessertDao {

    @Query("SELECT * FROM desserts WHERE id = :id")
    fun getOne(id: Int): Flow<DessertEntity?>

    @Query("SELECT * FROM desserts WHERE isFavorite = 1 ORDER BY name")
    fun getFavorites(): Flow<List<DessertEntity>>

    @Upsert
    suspend fun upsert(dessert: DessertEntity)

    @Query("SELECT EXISTS(SELECT * FROM desserts WHERE id = :id AND isFavorite = 1)")
    suspend fun isFavorite(id: Int): Boolean

    @Query("UPDATE desserts SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: Int, isFavorite: Boolean)
}