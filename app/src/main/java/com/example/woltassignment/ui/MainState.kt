package com.example.woltassignment.ui

import com.example.woltassignment.domain.model.Restaurant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class MainState(
    val restaurants: ImmutableList<Restaurant> = persistentListOf()
)