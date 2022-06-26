package com.example.a20220624_movieapplication_juanmartinezleon.view.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a20220624_movieapplication_juanmartinezleon.cons.Constants
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.MovieItemBinding
import com.example.a20220624_movieapplication_juanmartinezleon.model.Genre
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie

class MovieItemAdapter(
    private val moviesList: MutableList<Movie> = mutableListOf(),
    private val genreList: List<Genre> = listOf(),
    private val fetchNextMovies: () -> Unit,
    private val openDetails: (Int?) -> Unit
): RecyclerView.Adapter<MovieItemAdapter.MovieItemViewHolder>() {

    private val cons = Constants

    fun setMovies(newList: List<Movie>, bNewOrAdd: Boolean) {
        //true to clear the list and set, false to add new element to the list
        if (bNewOrAdd)
        moviesList.clear()

        moviesList.addAll(newList)
        // works like notifySetChanged, but without the warning
        notifyItemRangeChanged(0, itemCount)
    }

    inner class MovieItemViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(movieData: Movie) {
            binding.tvMovieName.text = movieData.title
            Glide.with(binding.ivPoster)
                .load("${cons.imageUrl}${movieData.posterPath}")
                .into(binding.ivPoster)

            binding.tvPopularityScore.text = movieData.popularity?.toInt().toString()
            binding.tvReleaseYear.text = movieData.releaseDate?.substring(0,4)

            val movieGenres: MutableList<Genre> = mutableListOf()

            movieData.genreIds?.forEach { id ->
                var genre: Genre = genreList.single { it.id == id }
                movieGenres.add(genre)
            }
            val genresAdapter = GenresAdapter(movieGenres)
            binding?.rvGenres?.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL,false)
            binding.rvGenres.adapter = genresAdapter

            binding.root.setOnClickListener {
                openDetails(movieData.id)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MovieItemViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.onBind(moviesList[position])
        if (position  == moviesList.size - 5)
            fetchNextMovies()
    }

    override fun getItemCount() = moviesList.size
}