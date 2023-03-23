package com.example.rickmortyretrofit.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results(
    val created: String,
    val episode: MutableList<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    var isLike: Boolean

): Parcelable