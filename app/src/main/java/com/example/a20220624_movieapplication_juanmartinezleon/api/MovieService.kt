package com.example.a20220624_movieapplication_juanmartinezleon.api

import com.example.a20220624_movieapplication_juanmartinezleon.BuildConfig
import com.example.a20220624_movieapplication_juanmartinezleon.model.GenreResponse
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie
import com.example.a20220624_movieapplication_juanmartinezleon.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String? = BuildConfig.APIKEY,
        @Query("language") language: String? = null,
        @Query("page") page: Int? = null,
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String? = BuildConfig.APIKEY,
        @Query("language") language: String? = null
    ): Response<GenreResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int?,
        @Query("api_key") apiKey: String? = BuildConfig.APIKEY,
    ): Response<Movie>
}