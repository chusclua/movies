package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieCast
import com.chus.clua.presentation.model.CastList


fun MovieCast.toCastList() =
    CastList(
        id = id,
        character = character,
        name = name,
        profileUrl = buildImageUrl(smallProfilePath = profilePath)
    )