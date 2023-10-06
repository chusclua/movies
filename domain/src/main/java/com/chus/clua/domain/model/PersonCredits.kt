package com.chus.clua.domain.model


data class PersonCredits(
    val id: Int,
    val cast: List<PersonMovieCast>,
    val crew: List<PersonMovieCrew>
)
