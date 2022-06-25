package com.example.a20220624_movieapplication_juanmartinezleon.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a20220624_movieapplication_juanmartinezleon.cons.Constants
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.MovieItemBinding
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie

class MovieItemAdapter(
    private val moviesList: MutableList<Movie> = mutableListOf(),
): RecyclerView.Adapter<MovieItemAdapter.PlayerRunesViewHolder>() {

    private val cons = Constants

    fun setMovies(newList: List<Movie>) {
        moviesList.clear()
        moviesList.addAll(newList)
        // works like notifySetChanged, but without the warning
        notifyItemRangeChanged(0, itemCount)
    }

    inner class PlayerRunesViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(movieData: Movie) {
            binding.tvMovieName.text = movieData.title
            Glide.with(binding.ivPoster)
                .load("${cons.imageUrl}${movieData.posterPath}")
                .into(binding.ivPoster)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerRunesViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PlayerRunesViewHolder, position: Int) {
        holder.onBind(moviesList[position])
    }

    override fun getItemCount() = moviesList.size
}