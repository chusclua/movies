package com.chus.clua.domain.model



data class PersonDetail(
    val detail: PersonDataDetail,
    val cast: List<PersonMovieCast>,
    val crew: List<PersonMovieCrew>
)
