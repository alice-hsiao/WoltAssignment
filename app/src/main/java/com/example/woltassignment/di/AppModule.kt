package com.example.woltassignment.di

import com.example.woltassignment.data.WoltApi
import com.example.woltassignment.domain.MainRepository

interface AppModule {
    val woltApi: WoltApi
    val mainRepository: MainRepository
}