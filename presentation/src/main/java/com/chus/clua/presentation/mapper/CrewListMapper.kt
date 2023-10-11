package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieCrew
import com.chus.clua.presentation.model.CrewList


fun MovieCrew.toCrewList() =
    CrewList(
        id = id,
        job = job,
        name = name,
        profilePath = "$BASE_PROFILE_PATH$profilePath"
    )


private const val BASE_PROFILE_PATH = "https://image.tmdb.org/t/p/w185"