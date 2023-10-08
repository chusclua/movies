package com.chus.clua.presentation.model



data class PersonMovieCastList(
    override val id: Int,
    override val posterPath: String,
    val character: String,
) : PersonMovieList
