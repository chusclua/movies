package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieDataDetail
import com.chus.clua.presentation.model.MovieDetail

fun MovieDataDetail.toMovieDetail() =
    MovieDetail(
        backdropPath = "$BASE_BACKDROP_PATH$backdropPath",
        genres = genres,
        id = id,
        overview = overview,
        posterPath = "$BASE_POSTER_PATH$posterPath",
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        releaseDate = releaseDate,
        tagline = tagline,
        title = title,
        voteAverage = voteAverage,
    )

private const val BASE_POSTER_PATH = "https://image.tmdb.org/t/p/w342"
private const val BASE_BACKDROP_PATH = "https://image.tmdb.org/t/p/w780"