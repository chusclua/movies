package com.chus.clua.presentation.movie_detail

import com.chus.clua.presentation.model.CastList
import com.chus.clua.presentation.model.CrewList
import com.chus.clua.presentation.model.MovieDetailUi
import com.chus.clua.presentation.model.VideoList

data class MovieDetailViewState(
    val isFavorite: Boolean = false,
    val movieDetail: MovieDetailUi? = null,
    val cast: List<CastList> = emptyList(),
    val crew: List<CrewList> = emptyList(),
    val videos: List<VideoList> = emptyList(),
    val error: Boolean = false
)
