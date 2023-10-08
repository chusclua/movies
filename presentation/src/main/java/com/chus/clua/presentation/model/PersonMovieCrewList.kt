package com.chus.clua.presentation.model


data class PersonMovieCrewList(
    override val id: Int,
    override val posterPath: String,
    val job: String
) : PersonMovieList

