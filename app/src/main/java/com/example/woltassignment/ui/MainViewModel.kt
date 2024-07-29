package com.example.woltassignment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.woltassignment.core.ApiResponse
import com.example.woltassignment.core.DispatcherProvider
import com.example.woltassignment.di.AppModule
import com.example.woltassignment.domain.MainRepository
import com.example.woltassignment.domain.model.Restaurant
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val dispatcher: DispatcherProvider,
    private val mainRepository: MainRepository,
) : ViewModel() {
    private val _effect = Channel<MainEffect>()
    val effect = _effect.receiveAsFlow()

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = combine(
        _state.asStateFlow(),
        mainRepository.restaurants()
    ) { mainState, restaurantsResponse ->
        handleCombinedFlows(mainState, restaurantsResponse)
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

    private fun handleCombinedFlows(
        mainState: MainState,
        response: ApiResponse<List<Restaurant>, Throwable>
    ): MainState {
        return when (response) {
            is ApiResponse.Success -> {
                val newRestaurants = response.response.map { restaurant ->
                    val liked = mainState.restaurants
                        .find { it.id == restaurant.id }?.liked == true

                    restaurant.copy(liked = liked)
                }

                MainState(restaurants = newRestaurants.toImmutableList())
            }

            else -> {
                viewModelScope.launch(dispatcher.main) {
                    _effect.send(MainEffect.ShowSnackbar(response.toString()))
                }
                mainState
            }
        }
    }

    companion object {
        fun Factory(appModule: AppModule) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(appModule.dispatcherProvider, appModule.mainRepository) as T
            }
        }
    }

    sealed interface MainAction {
        data class OnLikeClicked(val id: String, val isLiked: Boolean) : MainAction
    }

    sealed interface MainEffect {
        data class ShowSnackbar(val error: String) : MainEffect
    }
}