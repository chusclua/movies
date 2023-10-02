package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.Movie
import com.chus.clua.presentation.extensions.toYear
import com.chus.clua.presentation.model.MovieList
import java.text.SimpleDateFormat
import java.util.Calendar


fun Movie.toMovieList() =
    MovieList(
        id = id,
        title = title,
        posterPath = "$BASE_POSTER_PATH$posterPath",
        year = releaseDate.toYear(),
        voteAverage = voteAverage
    )

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"