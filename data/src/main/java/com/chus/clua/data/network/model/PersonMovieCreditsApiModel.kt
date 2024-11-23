package com.chus.clua.data.network.model

data class PersonMovieCreditsApiModel(
    val id: Int,
    val cast: List<PersonMovieCastApiModel>,
    val crew: List<PersonMovieCrewApiModel>,
)
