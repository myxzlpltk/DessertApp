package com.bangkit.dessert.favorite

import androidx.lifecycle.ViewModel
import com.bangkit.dessert.core.domain.usecase.DessertUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(
    dessertUseCase: DessertUseCase
) : ViewModel() {

    val favoriteDessertListFlow = dessertUseCase.getFavorites()
}