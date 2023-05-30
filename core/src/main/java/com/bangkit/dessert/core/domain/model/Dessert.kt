package com.bangkit.dessert.core.domain.model

data class Dessert(
    val id: Int,
    val name: String,
    val image: String,
    val area: String,
    val ingredients: List<Ingredient>,
    val instructions: String,
    val isFavorite: Boolean = false
)
