package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.PersonMovieCrew
import com.chus.clua.presentation.model.PersonMovieCrewList

fun PersonMovieCrew.toPersonMovieCrewList() =
    PersonMovieCrewList(
        id = id,
        posterUrl = buildImageUrl(posterPath = posterPath),
        job = job
    )
