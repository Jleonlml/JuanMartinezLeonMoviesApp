package com.example.a20220624_movieapplication_juanmartinezleon.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreResponse(
    var genres: List<Genre>
): Parcelable

@Parcelize
data class Genre(
    var id: Int? = null,
    var name: String? = null
): Parcelable