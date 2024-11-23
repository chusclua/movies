package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieCrew
import com.chus.clua.presentation.model.CrewList

fun MovieCrew.toCrewList() =
    CrewList(
        id = id,
        job = job,
        name = name,
        profileUrl = buildImageUrl(smallProfilePath = profilePath)
    )
