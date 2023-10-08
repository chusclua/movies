package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.Movie
import com.chus.clua.presentation.extensions.toYear
import com.chus.clua.presentation.model.FavoriteMovieList


fun Movie.toFavoriteMovieList() =
    FavoriteMovieList(
        id = id,
        title = title,
        backdropPath = "$BASE_POSTER_PATH$backdropPath",
        year = releaseDate.toYear(),
        voteAverage = voteAverage
    )

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"