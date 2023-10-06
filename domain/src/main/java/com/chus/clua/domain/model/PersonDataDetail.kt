package com.chus.clua.domain.model


data class PersonDataDetail(
    val adult: Boolean,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String?,
    val deathDay: String?,
    val gender: Gender,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String?,
    val popularity: Double,
    val profilePath: String?
)
