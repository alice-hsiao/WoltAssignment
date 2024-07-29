package com.example.woltassignment.data

import com.example.woltassignment.data.dto.RestaurantDTO
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://restaurant-api.wolt.com/v1/"

interface WoltApi {

    @GET("pages/restaurants")
    suspend fun getRestaurants(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): RestaurantDTO
}