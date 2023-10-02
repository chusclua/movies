package com.chus.clua.domain.model


data class MovieCast(
    val id: Int,
    val character: String,
    val name: String,
    val profilePath: String?,
    val popularity: Double,
)
