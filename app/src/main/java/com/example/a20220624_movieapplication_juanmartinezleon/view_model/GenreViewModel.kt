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

class GenreViewModel(
    private val repositoryImpl: MovieRepositoryImp,
    private val dispatcher: CoroutineDispatcher
): ViewModel() {
    private val tag = "GenreViewModel"
    private val viewModelSafeScope by lazy {
        viewModelScope + coroutineExceptionHandler
    }

    // For logging errors of the coroutine
    private val coroutineExceptionHandler by lazy {
        CoroutineExceptionHandler { coroutineContext, throwable ->
            Log.e(tag, "Context: $coroutineContext\nMessage: ${throwable.localizedMessage}", throwable)
        }
    }

    private val _genreLiveData = MutableLiveData<UiState>()
    val genreLiveData: LiveData<UiState> get() = _genreLiveData

    fun getGenres(language: String?) {
        viewModelSafeScope.launch(dispatcher) {
            val response = repositoryImpl.getGenres(language = language).collect {
                _genreLiveData.postValue(it)
            }
        }
    }

    fun setLoadingState() { _genreLiveData.value = UiState.Loading }
}