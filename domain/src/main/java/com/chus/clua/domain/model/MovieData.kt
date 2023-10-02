package com.chus.clua.domain.model


data class MovieData(
    val backdropPath: String?,
    val belongsToCollection: MovieCollection?,
    val genres: List<String>,
    val id: Int,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<String>,
    val productionCountries: List<String>,
    val releaseDate: String,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)
