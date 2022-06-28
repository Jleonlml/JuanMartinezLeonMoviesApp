package com.example.a20220624_movieapplication_juanmartinezleon.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.model.Genre
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie
import com.example.a20220624_movieapplication_juanmartinezleon.repository.MovieRepositoryImp
import com.example.a20220624_movieapplication_juanmartinezleon.view.adapters.MovieItemAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class MovieViewModel(
    private val repositoryImpl: MovieRepositoryImp,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val tag = "SchoolViewModel"
    var currentMovieList: MutableList<Movie> = mutableListOf()
    var bFirstLoad = true
    private var bGetGenres = true
    var pageCounter: Int = 1
    lateinit var movieItemAdapter: MovieItemAdapter
    lateinit var genreList: List<Genre>

    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    init {
        getGenres("en-US")
    }

    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(tag, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _moviesLiveData = MutableLiveData<UiState>()
    val moviesLiveData: LiveData<UiState> get() = _moviesLiveData

    fun getMovies(language: String?, page: Int?) {
        Log.d("state2", "get movies")
        viewModelSafeScope.launch(dispatcher) {
            val movieResponse = repositoryImpl.getMovies(language = language, page= page).collect {
                pageCounter ++
                _moviesLiveData.postValue(it)
            }
        }
    }

    private fun getGenres(language: String) {
        CoroutineScope(Dispatchers.IO).async {
            val genreResponse = repositoryImpl.getGenres(language = language)
            genreList= genreResponse.genres
        }
    }

    fun setMovieLoadingState() { _moviesLiveData.value = UiState.Loading }

}