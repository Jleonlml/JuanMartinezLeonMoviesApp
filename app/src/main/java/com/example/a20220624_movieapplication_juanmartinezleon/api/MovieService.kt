package com.example.a20220624_movieapplication_juanmartinezleon.api

import com.example.a20220624_movieapplication_juanmartinezleon.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("popular?language=en-US&page=1")
    suspend fun getMovies(
        @Query("api_key") apiKey: String? = "2430e131346c2e053a1aaa36df5ae882",
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): Response<MovieResponse>
}