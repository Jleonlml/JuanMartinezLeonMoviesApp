package com.example.a20220624_movieapplication_juanmartinezleon.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.repository.MovieRepositoryImp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

class MovieDetailViewModel (
    private val repositoryImpl: MovieRepositoryImp,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val tag = "MovieDetailViewModel"
    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(tag, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _movieDetailsLiveData = MutableLiveData<UiState>()
    val movieDetailsLiveData: LiveData<UiState> get() = _movieDetailsLiveData

    fun getMovieDetails(movieId: Int?) {
        viewModelSafeScope.launch(dispatcher) {
            val response = repositoryImpl.getMovieDetails(movieId = movieId).collect {
                _movieDetailsLiveData.postValue(it)
            }
        }
    }

    fun setMovieDetailsLoadingState() { _movieDetailsLiveData.value = UiState.Loading }
}