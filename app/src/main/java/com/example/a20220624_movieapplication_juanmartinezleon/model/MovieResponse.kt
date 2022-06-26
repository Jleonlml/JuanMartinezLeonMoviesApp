package com.example.a20220624_movieapplication_juanmartinezleon.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieResponse (
        val page: Int?,
        val results: List<Movie>,
): Parcelable

@Parcelize
data class Movie (
        @SerializedName("title") var title : String? = null,
        @SerializedName("poster_path") var posterPath : String? = null,
        @SerializedName("popularity") var popularity : Double? = null,
        @SerializedName("release_date") var releaseDate : String? = null,
        @SerializedName("genre_ids") var genreIds : List<Int>? = null

): Parcelable
