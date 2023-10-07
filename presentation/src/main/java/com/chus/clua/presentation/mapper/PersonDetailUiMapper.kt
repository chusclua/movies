package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.PersonDataDetail
import com.chus.clua.presentation.model.PersonDetailUi


fun PersonDataDetail.toPersonDetailUi() =
    PersonDetailUi(
        biography = biography,
        homepage = homepage?.replace("http", "https"),
        id = id,
        name = name,
        popularity = popularity,
        profilePath = "$BASE_PROFILE_PATH$profilePath"
    )


private const val BASE_PROFILE_PATH = "https://image.tmdb.org/t/p/h632"