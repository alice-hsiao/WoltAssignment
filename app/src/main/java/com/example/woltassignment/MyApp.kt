package com.example.woltassignment

import android.app.Application
import com.example.woltassignment.di.AppModule
import com.example.woltassignment.di.AppModuleImpl

class MyApp : Application() {
    companion object {
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModuleImpl()
    }
}