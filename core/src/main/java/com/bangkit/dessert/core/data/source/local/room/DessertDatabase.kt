package com.bangkit.dessert.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bangkit.dessert.core.data.source.local.entity.DessertBriefEntity
import com.bangkit.dessert.core.data.source.local.entity.DessertEntity

@Database(entities = [DessertEntity::class, DessertBriefEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DessertDatabase : RoomDatabase() {
    abstract fun dessertDao(): DessertDao
    abstract fun dessertBriefDao(): DessertBriefDao
}