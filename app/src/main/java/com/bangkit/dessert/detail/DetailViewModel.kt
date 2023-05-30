package com.bangkit.dessert.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bangkit.dessert.core.data.Resource
import com.bangkit.dessert.core.domain.usecase.DessertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dessertUseCase: DessertUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = checkNotNull<Int>(savedStateHandle[DetailActivity.EXTRA_ID])

    val dessertFlow = dessertUseCase.getOne(id).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading()
    )

    fun refresh() {
        viewModelScope.launch {
            dessertUseCase.refreshOne(id)
        }
    }

    fun setFavorite(isFavorite: Boolean) {
        viewModelScope.launch {
            dessertUseCase.setFavorite(id, isFavorite)
        }
    }
}