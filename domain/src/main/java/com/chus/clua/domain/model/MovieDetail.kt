package com.chus.clua.domain.model

data class MovieDetail(
    val isFavorite: Boolean,
    val movieData: MovieDataDetail,
    val cast: List<MovieCast>,
    val crew: List<MovieCrew>,
    val videos: List<MovieVideo>
)
