package com.bangkit.dessert.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.dessert.core.data.source.local.entity.DessertBriefEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DessertBriefDao {

    @Query("SELECT * FROM dessert_briefs ORDER BY name")
    fun getAll(): Flow<List<DessertBriefEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(desserts: List<DessertBriefEntity>)

    @Query("DELETE FROM dessert_briefs")
    suspend fun clearAll()
}