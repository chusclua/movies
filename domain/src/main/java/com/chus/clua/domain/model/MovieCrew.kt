package com.chus.clua.domain.model

data class MovieCrew(
    val id: Int,
    val job: String,
    val name: String,
    val profilePath: String?,
    val popularity: Double,
)
