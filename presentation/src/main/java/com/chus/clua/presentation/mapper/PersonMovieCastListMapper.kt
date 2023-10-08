package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.PersonMovieCast
import com.chus.clua.presentation.model.PersonMovieCastList


fun PersonMovieCast.toPersonMovieCastList() =
    PersonMovieCastList(
        id = id,
        posterPath = "$BASE_POSTER_PATH$posterPath",
        character = character
    )

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"