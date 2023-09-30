package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.MovieResponseModel
import com.chus.clua.domain.model.Movie


fun MovieResponseModel.toMovie() =
    Movie(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
