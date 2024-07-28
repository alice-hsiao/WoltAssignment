package com.example.woltassignment.data

import com.example.woltassignment.domain.model.Restaurant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val BASE_URL = "https://restaurant-api.wolt.com/v1/"

val woltApi = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(WoltApi::class.java)

interface WoltApi {

    @GET("pages/restaurants")
    suspend fun getRestaurants(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double
    ): RestaurantDO
}