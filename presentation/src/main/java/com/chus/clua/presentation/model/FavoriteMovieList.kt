package com.chus.clua.presentation.model


data class FavoriteMovieList(
    val id: Int,
    val title: String,
    val backdropPath: String,
    val year: String,
    val voteAverage: Double,
)
