package com.chus.clua.presentation.detail

import com.chus.clua.presentation.model.CastList
import com.chus.clua.presentation.model.CrewList
import com.chus.clua.presentation.model.MovieDetail

data class DetailViewState(
    val movieDetail: MovieDetail? = null,
    val cast: List<CastList> = emptyList(),
    val crew: List<CrewList> = emptyList(),
    val error: Boolean = false
)