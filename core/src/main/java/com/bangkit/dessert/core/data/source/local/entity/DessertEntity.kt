package com.bangkit.dessert.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bangkit.dessert.core.domain.model.Ingredient

@Entity(tableName = "desserts")
data class DessertEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val image: String,
    val area: String,
    val ingredients: List<Ingredient>,
    val instructions: String,
    val isFavorite: Boolean = false,
)
