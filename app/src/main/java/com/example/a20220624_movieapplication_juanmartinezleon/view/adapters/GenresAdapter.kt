package com.example.a20220624_movieapplication_juanmartinezleon.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a20220624_movieapplication_juanmartinezleon.cons.Constants
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.GenreItemBinding
import com.example.a20220624_movieapplication_juanmartinezleon.databinding.MovieItemBinding
import com.example.a20220624_movieapplication_juanmartinezleon.model.Genre
import com.example.a20220624_movieapplication_juanmartinezleon.model.Movie

class GenresAdapter(
    private val genresList: MutableList<Genre> = mutableListOf(),
): RecyclerView.Adapter<GenresAdapter.GenreItemViewHolder>() {

    private val cons = Constants

    inner class GenreItemViewHolder(
        private val binding: GenreItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(genreData: Genre) {
            binding.tvGenre.text = genreData.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        GenreItemViewHolder(
            GenreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: GenreItemViewHolder, position: Int) {
        holder.onBind(genresList[position])
    }

    override fun getItemCount() = genresList.size
}