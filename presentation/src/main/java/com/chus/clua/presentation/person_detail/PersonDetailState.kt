package com.chus.clua.presentation.person_detail

import com.chus.clua.presentation.model.PersonDetailUi
import com.chus.clua.presentation.model.PersonMovieCastList
import com.chus.clua.presentation.model.PersonMovieCrewList

data class PersonDetailState(
    val detail: PersonDetailUi? = null,
    val cast: List<PersonMovieCastList> = emptyList(),
    val crew: List<PersonMovieCrewList> = emptyList(),
    val error: Boolean = false
)
