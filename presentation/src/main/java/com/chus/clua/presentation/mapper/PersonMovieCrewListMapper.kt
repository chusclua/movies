package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.PersonMovieCrew
import com.chus.clua.presentation.model.PersonMovieCrewList


fun PersonMovieCrew.toPersonMovieCrewList() =
    PersonMovieCrewList(
        id = id,
        posterPath = "$BASE_POSTER_PATH$posterPath",
        job = job
    )

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"