package com.example.woltassignment

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.woltassignment.data.MainRepositoryImpl
import com.example.woltassignment.data.woltApi
import com.example.woltassignment.domain.MainRepository
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.log


class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> =
        combine(_state.asStateFlow(), mainRepository.restaurants()) { mainState, restaurants ->
            val newRestaurants = restaurants.map { newRestaurants ->
                val liked = mainState.restaurants
                    .find { it.id == newRestaurants.id }?.liked == true

                newRestaurants.copy(liked = liked)
            }
            Log.i("MainViewModel", "new refresh $newRestaurants")
            MainState(restaurants = newRestaurants.toImmutableList())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = MainState()
        )

    fun processAction(action: MainAction) {
        when (action) {
            is MainAction.OnLikeClicked -> onLikeRestaurantClicked(
                id = action.id,
                isLiked = action.isLiked
            )
        }
    }

    private fun onLikeRestaurantClicked(id: String, isLiked: Boolean) {
        val updatedRestaurants = state.value.restaurants.map {
            if (it.id == id) {
                it.copy(liked = isLiked)
            } else {
                it
            }
        }
        _state.update { it.copy(restaurants = updatedRestaurants.toImmutableList()) }
    }

    companion object {
        val Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(MainRepositoryImpl(woltApi = woltApi)) as T
            }
        }
    }

    sealed interface MainAction {
        data class OnLikeClicked(val id: String, val isLiked: Boolean) : MainAction
    }
}