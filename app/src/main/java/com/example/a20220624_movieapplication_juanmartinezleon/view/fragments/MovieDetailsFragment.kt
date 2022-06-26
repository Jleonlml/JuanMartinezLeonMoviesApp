package com.example.a20220624_movieapplication_juanmartinezleon.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.a20220624_movieapplication_juanmartinezleon.cons.Constants
import com.example.a20220624_movieapplication_juanmartinezleon.cons.UiState
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.MovieDetailsBinding
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie
import com.example.a20220624_movieapplication_juanmartinezleon.view.adapters.GenresAdapter
import com.example.a20220624_movieapplication_juanmartinezleon.view_model.MovieDetailViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment: Fragment() {

    private var _binding: MovieDetailsBinding? = null
    private val binding: MovieDetailsBinding? get() = _binding
    private val movieDetailsViewModel by sharedViewModel<MovieDetailViewModel>()
    private val args: MovieDetailsFragmentArgs by navArgs()
    private val cons = Constants


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MovieDetailsBinding.inflate(layoutInflater)
        configureObserver()
        return  binding?.root
    }

    private fun configureObserver() {
        movieDetailsViewModel.movieDetailsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UiState.Success -> {
                    val data = (state.data as Movie)
                    Glide.with(binding!!.ivPoster)
                        .load("${cons.imageUrl}${data.posterPath}")
                        .into(binding!!.ivPoster)

                    binding!!.tvMovieName.text = data.title
                    binding!!.tvPopularityScore.text = data.popularity?.toInt().toString()
                    binding!!.tvReleaseYear.text = data.releaseDate?.substring(0,4)
                    binding!!.tvRuntime.text = data.runtime.toString()
                    binding!!.tvHomepage.text = data.homepage
                    binding!!.tvHomepage.setOnClickListener {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(data.homepage)
                        startActivity(openURL)
                    }

                    val genresAdapter = GenresAdapter(data.genres?.toMutableList()!!)
                    binding?.rvGenres?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    binding?.rvGenres?.adapter = genresAdapter
                }
                is UiState.Error -> {
                }
                is UiState.Loading -> {
                    movieDetailsViewModel.getMovieDetails(args.movieId)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}