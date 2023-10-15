package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.Movie
import com.chus.clua.presentation.extensions.toYear
import com.chus.clua.presentation.model.MovieList


fun Movie.toMovieList() =
    MovieList(
        id = id,
        title = title,
        backdropPath = buildImageUrl(backDropPath = backdropPath),
        posterPath = buildImageUrl(posterPath = posterPath),
        year = releaseDate.toYear(),
        voteAverage = voteAverage
    )