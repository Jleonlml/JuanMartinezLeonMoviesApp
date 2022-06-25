package com.example.a20220624_movieapplication_juanmartinezleon.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.MovieListBinding
import com.example.a20220624_movieapplication_juanmartinezleon.model.MovieResponse
import com.example.a20220624_movieapplication_juanmartinezleon.view.adapters.MovieItemAdapter
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment: Fragment() {
    private var _binding: MovieListBinding? = null
    private val binding: MovieListBinding? get() = _binding

    private val movieViewModel by viewModel<MovieViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieListBinding.inflate(layoutInflater)
        movieViewModel.setLoadingState()
        configureObserver()
        return  binding?.root
    }

    private fun configureObserver() {
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    binding?.rvMovies?.adapter = MovieItemAdapter(
                        moviesList = (state.data as MovieResponse).results.toMutableList(),
                    )
                }
                is UiState.Error -> {
                }
                is UiState.Loading -> {
                    movieViewModel.getMovies(language = "en-US", 1)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}