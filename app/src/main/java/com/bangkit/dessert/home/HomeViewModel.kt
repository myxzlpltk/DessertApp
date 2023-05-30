package com.bangkit.dessert.home

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
class HomeViewModel @Inject constructor(
    private val dessertUseCase: DessertUseCase
) : ViewModel() {

    val dessertListFlow = dessertUseCase.getAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Resource.Loading()
    )

    fun refresh() {
        viewModelScope.launch {
            dessertUseCase.refresh()
        }
    }
}