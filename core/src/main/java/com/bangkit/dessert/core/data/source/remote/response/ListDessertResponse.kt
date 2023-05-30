package com.bangkit.dessert.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListDessertResponse(

	@field:SerializedName("meals")
	val meals: List<DessertBriefItemResponse>
)

data class DessertBriefItemResponse(

	@field:SerializedName("idMeal")
	val idMeal: Int,

	@field:SerializedName("strMeal")
	val strMeal: String,

	@field:SerializedName("strMealThumb")
	val strMealThumb: String,
)
