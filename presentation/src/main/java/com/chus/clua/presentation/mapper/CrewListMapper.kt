package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.presentation.model.CrewList


fun MovieCredits.toCrewList() = this.crew.map { cast ->
    CrewList(
        id = cast.id,
        job = cast.job,
        name = cast.name,
        profilePath = "$BASE_PROFILE_PATH${cast.profilePath}"
    )
}

private const val BASE_PROFILE_PATH = "https://image.tmdb.org/t/p/w185"