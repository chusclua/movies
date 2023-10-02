package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.presentation.model.CastList


fun MovieCredits.toCastList() = this.cast.map { cast ->
    CastList(
        id = cast.id,
        character = cast.character,
        name = cast.name,
        profilePath = "$BASE_PROFILE_PATH${cast.profilePath}"
    )
}

private const val BASE_PROFILE_PATH = "https://image.tmdb.org/t/p/w185"