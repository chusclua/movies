package com.chus.clua.presentation.model

data class MovieList(
    val id: Int,
    val title: String,
    val backdropUrl: String,
    val posterUrl: String,
    val year: String,
    val voteAverage: Double,
)
