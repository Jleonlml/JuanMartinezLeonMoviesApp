package com.example.a20220624_movieapplication_juanmartinezleon.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MoviesApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}