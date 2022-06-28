package com.example.a20220624_movieapplication_juanmartinezleon.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.MovieListBinding
import com.example.a20220624_movieapplication_juanmartinezleon.model.MovieResponse
import com.example.a20220624_movieapplication_juanmartinezleon.view.adapters.MovieItemAdapter
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieDetailViewModel
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class MovieListFragment: Fragment() {
    private var _binding: MovieListBinding? = null
    private val binding: MovieListBinding? get() = _binding

    private val movieViewModel by sharedViewModel<MovieViewModel>()
    private val movieDetailsViewModel by sharedViewModel<MovieDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieListBinding.inflate(layoutInflater)
        movieViewModel.bFirstLoad = true
        configureObserver()
        return  binding?.root
    }

    private fun configureObserver() {
        movieViewModel.moviesLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val nextMoviesToAdd = (state.data as MovieResponse).results
                    movieViewModel.currentMovieList.addAll(nextMoviesToAdd)
                    if (movieViewModel.bFirstLoad) {
                        movieViewModel.movieItemAdapter = MovieItemAdapter(
                            moviesList = movieViewModel.currentMovieList,
                            genreList = movieViewModel.genreList,
                            fetchNextMovies = ::fetchNextMovies,
                            openDetails = ::openDetails,
                        )
                        binding?.rvMovies?.adapter = movieViewModel.movieItemAdapter
                        movieViewModel.bFirstLoad = false
                    }
                    else {
                        movieViewModel.movieItemAdapter.setMovies(nextMoviesToAdd, false)
                    }
                }
                is UiState.Error -> {
                }
                is UiState.Loading -> {
                        movieViewModel.getMovies(language = "en-US", movieViewModel.pageCounter)
                }
            }
        }
    }

    private fun openDetails(movieId: Int?) {
        movieDetailsViewModel.setMovieDetailsLoadingState()
        val action = MovieListFragmentDirections.actionListToDetails(movieId!!)
        findNavController().navigate(action)
    }

    private fun fetchNextMovies() {
        movieViewModel.setMovieLoadingState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}