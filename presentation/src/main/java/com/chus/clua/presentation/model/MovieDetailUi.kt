package com.chus.clua.presentation.model

data class MovieDetailUi(
    val backdropUrl: String,
    val genres: List<String>,
    val id: Int,
    val overview: String,
    val posterUrl: String,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val releaseDate: String,
    val tagline: String,
    val title: String,
    val voteAverage: Double
)
