package com.example.woltassignment

import com.example.woltassignment.core.ApiResponse
import com.example.woltassignment.domain.MainRepository
import com.example.woltassignment.domain.model.Restaurant
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class FakeMainRepository : MainRepository {
    val channel = Channel<ApiResponse<List<Restaurant>, Throwable>>()

    override fun restaurants(): Flow<ApiResponse<List<Restaurant>, Throwable>> =
        channel.receiveAsFlow()
}