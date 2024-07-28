package com.example.woltassignment.data

import android.util.Log
import com.example.woltassignment.domain.MainRepository
import com.example.woltassignment.domain.model.Restaurant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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
private var position = 0


class MainRepositoryImpl(private val woltApi: WoltApi) : MainRepository {
    override fun restaurants(): Flow<List<Restaurant>> = flow {
        val coordinate = coordinates[position % INTERVAL_SEC]
        val restaurants = woltApi.getRestaurants(coordinate.first, coordinate.second)
        val restaurantsDTO = mutableListOf<Restaurant>()

        val section = restaurants.sections[1]

        for (j in section.items.indices) {
            if (restaurantsDTO.size > 14) {
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

        emit(restaurantsDTO)

        position++
        delay(INTERVAL_SEC * 1000L)
    }
}