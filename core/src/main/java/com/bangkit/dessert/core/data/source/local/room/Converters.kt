package com.bangkit.dessert.core.data.source.local.room

import androidx.room.TypeConverter
import com.bangkit.dessert.core.domain.model.Ingredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun listToString(list: List<String>): String = Gson().toJson(list)

    @TypeConverter
    fun stringToList(json: String): List<String> =
        Gson().fromJson(json, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun ingredientListToString(ingredients: List<Ingredient>): String = Gson().toJson(ingredients)

    @TypeConverter
    fun stringToIngredientList(json: String): List<Ingredient> =
        Gson().fromJson(json, object : TypeToken<List<Ingredient>>() {}.type)
}