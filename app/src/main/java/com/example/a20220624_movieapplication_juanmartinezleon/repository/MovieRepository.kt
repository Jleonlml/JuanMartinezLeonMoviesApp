package com.example.a20220624_movieapplication_juanmartinezleon.repository

import android.util.Log
import com.example.a20220624_movieapplication_juanmartinezleon.api.MovieService
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.model.Genre
import com.example.a20220624_movieapplication_juanmartinezleon.model.GenreResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.java.KoinJavaComponent.get

interface MovieRepository {
    suspend fun getMovies(language: String?, page: Int?): Flow<UiState>
    suspend fun getGenres(language: String?): GenreResponse
    suspend fun getMovieDetails(movieId: Int?): Flow<UiState>
}

class MovieRepositoryImp(
    private val service: MovieService  = get(MovieService::class.java)
): MovieRepository {
    override suspend fun getMovies(language: String?, page: Int?): Flow<UiState> =
        flow {
            try {
                // attempt some code
                val response = service.getMovies(language = language, page = page)
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UiState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    throw Exception("Failed network call")
                }
            } catch (e: Exception) {
                Log.d("state", e.message.toString())
                // catch the errors and run this block instead
                emit(UiState.Error(e))
            }
        }

    override suspend fun getGenres(language: String?): GenreResponse {
        val response = service.getGenres(language = language)

        return if (response.isSuccessful) {
            response.body()!!
        } else {
            GenreResponse(emptyList())
        }
    }


    override suspend fun getMovieDetails(movieId: Int?): Flow<UiState> =
        flow {
            try {
                // attempt some code
                val response = service.getMovieDetails(movieId = movieId)
                if (response.isSuccessful) {
                    emit(response.body()?.let {
                        UiState.Success(it)
                    } ?: throw Exception("Null Response"))
                } else {
                    throw Exception("Failed network call")
                }
            } catch (e: Exception) {
                Log.d("state", e.message.toString())
                // catch the errors and run this block instead
                emit(UiState.Error(e))
            }
        }
}