package com.example.woltassignment.di

import com.example.woltassignment.data.BASE_URL
import com.example.woltassignment.data.MainRepositoryImpl
import com.example.woltassignment.data.WoltApi
import com.example.woltassignment.domain.MainRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppModuleImpl : AppModule {
    override val woltApi: WoltApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WoltApi::class.java)
    }
    override val mainRepository: MainRepository by lazy {
        MainRepositoryImpl(woltApi)
    }
}