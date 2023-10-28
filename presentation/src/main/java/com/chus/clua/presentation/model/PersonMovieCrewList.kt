package com.chus.clua.presentation.model


data class PersonMovieCrewList(
    override val id: Int,
    override val posterUrl: String,
    val job: String
) : PersonMovieList

