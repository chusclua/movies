package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.CastResponse
import com.chus.clua.data.network.model.CrewResponse
import com.chus.clua.data.network.model.MovieCreditsResponse
import com.chus.clua.domain.model.MovieCast
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieCrew


fun MovieCreditsResponse.toMovieCredits() = MovieCredits(
    id = id,
    cast = cast.map { it.toMovieCast() },
    crew = crew.map { it.toMovieCrew() }
)

private fun CastResponse.toMovieCast() = MovieCast(
    id = id,
    character = character,
    name = name,
    profilePath = profilePath,
    popularity = popularity
)

private fun CrewResponse.toMovieCrew() = MovieCrew(
    id = id,
    job = job,
    name = name,
    profilePath = profilePath,
    popularity = popularity
)