package com.bangkit.dessert.core.utils

import com.bangkit.dessert.core.data.source.local.entity.DessertEntity
import com.bangkit.dessert.core.data.source.remote.response.DessertItemResponse
import com.bangkit.dessert.core.domain.model.Dessert
import com.bangkit.dessert.core.domain.model.Ingredient

object DessertMapper {

    fun fromLocal(dessert: DessertEntity): Dessert {
        return Dessert(
            id = dessert.id,
            name = dessert.name,
            image = dessert.image,
            area = dessert.area,
            ingredients = dessert.ingredients,
            instructions = dessert.instructions,
            isFavorite = dessert.isFavorite
        )
    }

    fun fromRemote(response: DessertItemResponse): Dessert {
        return Dessert(
            id = response.idMeal,
            name = response.strMeal,
            image = response.strMealThumb,
            area = response.strArea,
            ingredients = handleIngredients(response),
            instructions = response.strInstructions,
        )
    }

    fun toLocal(dessert: Dessert, isFavorite: Boolean? = null): DessertEntity {
        return DessertEntity(
            id = dessert.id,
            name = dessert.name,
            image = dessert.image,
            area = dessert.area,
            ingredients = dessert.ingredients,
            instructions = dessert.instructions,
            isFavorite = isFavorite ?: dessert.isFavorite,
        )
    }

    private fun handleIngredients(response: DessertItemResponse): List<Ingredient> {
        val ingredients = listOf(
            Pair(response.strIngredient1, response.strMeasure1),
            Pair(response.strIngredient2, response.strMeasure2),
            Pair(response.strIngredient3, response.strMeasure3),
            Pair(response.strIngredient4, response.strMeasure4),
            Pair(response.strIngredient5, response.strMeasure5),
            Pair(response.strIngredient6, response.strMeasure6),
            Pair(response.strIngredient7, response.strMeasure7),
            Pair(response.strIngredient8, response.strMeasure8),
            Pair(response.strIngredient9, response.strMeasure9),
            Pair(response.strIngredient10, response.strMeasure10),
            Pair(response.strIngredient11, response.strMeasure11),
            Pair(response.strIngredient12, response.strMeasure12),
            Pair(response.strIngredient13, response.strMeasure13),
            Pair(response.strIngredient14, response.strMeasure14),
            Pair(response.strIngredient15, response.strMeasure15),
            Pair(response.strIngredient16, response.strMeasure16),
            Pair(response.strIngredient17, response.strMeasure17),
            Pair(response.strIngredient18, response.strMeasure18),
            Pair(response.strIngredient19, response.strMeasure19),
            Pair(response.strIngredient20, response.strMeasure20),
        )

        return ingredients.mapNotNull { pair ->
            val (ingredient, measure) = pair

            if (ingredient.isNullOrBlank() || measure.isNullOrBlank()) {
                null
            } else {
                Ingredient(ingredient.trim(), measure.trim())
            }
        }
    }
}