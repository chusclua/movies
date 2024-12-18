package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.CollectionApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.domain.model.MovieCollection
import com.chus.clua.domain.model.MovieDataDetail

fun MovieDetailApiModel.toMovieDataDetail() =
    MovieDataDetail(
        backdropPath = backdropPath,
        belongsToCollection = belongsToCollection?.toMovieCollection(),
        genres = genres.map { it.name },
        id = id,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = productionCompanies.map { it.name },
        productionCountries = productionCountries.map { it.name },
        releaseDate = releaseDate,
        tagline = tagline,
        title = title,
        voteAverage = voteAverage,
        voteCount = voteCount,
    )

private fun CollectionApiModel.toMovieCollection() =
    MovieCollection(
        backdropPath = backdropPath,
        id = id,
        name = name,
        posterPath = posterPath,
    )
