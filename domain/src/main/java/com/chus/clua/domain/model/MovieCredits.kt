package com.chus.clua.domain.model


data class MovieCredits(
    val id: Int,
    val cast: List<MovieCast>,
    val crew: List<MovieCrew>
)
