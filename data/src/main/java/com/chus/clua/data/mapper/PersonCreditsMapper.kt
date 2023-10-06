package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.PersonMovieCastApiModel
import com.chus.clua.data.network.model.PersonMovieCreditsApiModel
import com.chus.clua.data.network.model.PersonMovieCrewApiModel
import com.chus.clua.domain.model.PersonCredits
import com.chus.clua.domain.model.PersonMovieCast
import com.chus.clua.domain.model.PersonMovieCrew


fun PersonMovieCreditsApiModel.toPersonCredits() =
    PersonCredits(
        id = id,
        cast = cast.map { it.toPersonMovieCast() },
        crew = crew.map { it.toPersonMovieCrew() }
    )

private fun PersonMovieCastApiModel.toPersonMovieCast() =
   PersonMovieCast(
       adult = adult,
       backdropPath = backdropPath,
       character = character,
       creditId = creditId,
       genreIds = genreIds,
       id = id,
       order = order,
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

private fun PersonMovieCrewApiModel.toPersonMovieCrew() =
    PersonMovieCrew(
        adult = adult,
        backdropPath = backdropPath,
        creditId = creditId,
        department = department,
        genreIds = genreIds,
        id = id,
        job = job,
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