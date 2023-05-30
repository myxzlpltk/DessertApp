package com.bangkit.dessert.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dessert_briefs")
data class DessertBriefEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
)
