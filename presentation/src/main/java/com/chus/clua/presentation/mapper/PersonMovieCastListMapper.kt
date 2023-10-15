package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.PersonMovieCast
import com.chus.clua.presentation.model.PersonMovieCastList


fun PersonMovieCast.toPersonMovieCastList() =
    PersonMovieCastList(
        id = id,
        posterPath = buildImageUrl(posterPath = posterPath),
        character = character
    )