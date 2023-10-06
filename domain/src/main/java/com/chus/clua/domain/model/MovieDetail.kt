package com.chus.clua.domain.model


data class MovieDetail(
    val isFavorite: Boolean,
    val movieData: MovieDataDetail?,
    val movieCredits: MovieCredits?,
    val movieVideos: MovieVideos?
)
