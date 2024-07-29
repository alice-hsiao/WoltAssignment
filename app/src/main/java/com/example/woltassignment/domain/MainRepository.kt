package com.example.woltassignment.domain

import com.example.woltassignment.core.ApiResponse
import com.example.woltassignment.domain.model.Restaurant
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun restaurants(): Flow<ApiResponse<List<Restaurant>, Throwable>>
}
