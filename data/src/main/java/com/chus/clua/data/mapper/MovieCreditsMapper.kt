package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.CastApiModel
import com.chus.clua.data.network.model.CrewApiModel
import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.domain.model.MovieCast
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieCrew


fun MovieCreditsApiModel.toMovieCredits() =
    MovieCredits(
        id = id,
        cast = cast.map { it.toMovieCast() },
        crew = crew.map { it.toMovieCrew() }
    )

private fun CastApiModel.toMovieCast() =
    MovieCast(
        id = id,
        character = character,
        name = name,
        profilePath = profilePath,
        popularity = popularity
    )

private fun CrewApiModel.toMovieCrew() =
    MovieCrew(
        id = id,
        job = job,
        name = name,
        profilePath = profilePath,
        popularity = popularity
    )