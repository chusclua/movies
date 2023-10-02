package com.chus.clua.presentation

import com.chus.clua.domain.model.Movie
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

private fun String.toYear(): String {
    return try {
        Calendar.getInstance().apply {
            this.time = SimpleDateFormat(DATE_FORMAT).parse(this@toYear)
        }.get(Calendar.YEAR).toString()
    } catch (e: Exception) {
        ""
    }
}

private const val DATE_FORMAT = "yyyy-MM-dd"

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"