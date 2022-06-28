package com.example.a20220624_movieapplication_juanmartinezleon.koin

import com.example.a20220624_movieapplication_juanmartinezleon.api.MovieService
import com.example.a20220624_movieapplication_juanmartinezleon.cons.Constants
import com.example.a20220624_movieapplication_juanmartinezleon.repository.MovieRepositoryImp
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieDetailViewModel
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<MovieService> {
        val cons = Constants

        Retrofit.Builder()
            .baseUrl(cons.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieService::class.java)
    }
    single { MovieRepositoryImp(get()) }
    single { Dispatchers.IO}
}

val viewModelModule = module {
    viewModel { MovieViewModel(get(), get()) }
    viewModel { MovieDetailViewModel(get(), get()) }
}