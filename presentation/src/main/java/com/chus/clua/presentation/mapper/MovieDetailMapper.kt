package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieDataDetail
import com.chus.clua.presentation.extensions.toPrettyDate
import com.chus.clua.presentation.model.MovieDetailUi

fun MovieDataDetail.toMovieDetail() =
    MovieDetailUi(
        backdropUrl = buildImageUrl(backDropPath = backdropPath),
        genres = genres,
        id = id,
        overview = overview,
        posterUrl = buildImageUrl(posterPath = posterPath),
        productionCompanies = productionCompanies,
        productionCountries = productionCountries,
        releaseDate = releaseDate.toPrettyDate(),
        tagline = tagline,
        title = title,
        voteAverage = voteAverage,
    )