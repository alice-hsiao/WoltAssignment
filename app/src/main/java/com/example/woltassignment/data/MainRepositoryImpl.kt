package com.example.woltassignment.data

import com.example.woltassignment.core.ApiResponse
import com.example.woltassignment.domain.MainRepository
import com.example.woltassignment.domain.model.Restaurant
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.UnknownHostException

private val coordinates = listOf(
    60.170187 to 24.930599,
    60.169418 to 24.931618,
    60.169818 to 24.932906,
    60.170005 to 24.935105,
    60.169108 to 24.936210,
    60.168355 to 24.934869,
    60.167560 to 24.932562,
    60.168254 to 24.931532,
    60.169012 to 24.930341,
    60.170085 to 24.929569
)
private const val INTERVAL_SEC = 10
private const val MAX_RESTAURANT = 15
private var position = 0

class MainRepositoryImpl(private val woltApi: WoltApi) : MainRepository {
    override fun restaurants(): Flow<ApiResponse<List<Restaurant>, Throwable>> = flow {
        while (true) {
            try {
                val coordinate = coordinates[position % INTERVAL_SEC]
                val restaurants = woltApi.getRestaurants(coordinate.first, coordinate.second)
                val restaurantsDTO = mutableListOf<Restaurant>()

                val section = restaurants.sections[1]

                for (j in section.items.indices) {
                    if (restaurantsDTO.size >= MAX_RESTAURANT) {
                        break
                    }
                    val item = section.items[j]
                    restaurantsDTO.add(
                        Restaurant(
                            id = item.venue.id,
                            description = item.venue.short_description,
                            name = item.venue.name,
                            url = item.image.url,
                            liked = false
                        )
                    )
                }
                position++
                emit(ApiResponse.Success(restaurantsDTO))

            } catch (e: Exception) {
                emit(
                    when (e) {
                        is UnknownHostException -> ApiResponse.Fail.NetworkError
                        is JsonSyntaxException -> ApiResponse.Fail.SerializationError
                        is HttpException -> ApiResponse.Fail.HttpError(e.code(), e)
                        else -> ApiResponse.Fail.Unknown
                    }
                )
            }
            delay(INTERVAL_SEC * 1000L)
        }
    }
}