package com.example.a20220624_movieapplication_juanmartinezleon.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie
import com.example.a20220624_movieapplication_juanmartinezleon.model.MovieResponse
import com.example.a20220624_movieapplication_juanmartinezleon.repository.MovieRepositoryImp
import com.example.a20220624_movieapplication_juanmartinezleon.view.adapters.MovieItemAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MovieViewModel(
    private val repositoryImpl: MovieRepositoryImp,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val tag = "SchoolViewModel"
    var currentMovieList: MutableList<Movie> = mutableListOf()
    var bFirstLoad = true
    var pageCounter: Int = 1
    lateinit var movieItemAdapter: MovieItemAdapter

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(tag, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _moviesLiveData = MutableLiveData<UiState>()
    val moviesLiveData: LiveData<UiState> get() = _moviesLiveData

    fun getMovies(language: String?, page: Int?) {
        viewModelSafeScope.launch(dispatcher) {
            val response = repositoryImpl.getMovies(language = language, page= page).collect {
                _moviesLiveData.postValue(it)
            }
        }
    }

    fun setMovieLoadingState() { _moviesLiveData.value = UiState.Loading }

}