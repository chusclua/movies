package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.PersonDataDetail
import com.chus.clua.presentation.model.PersonDetailUi


fun PersonDataDetail.toPersonDetailUi() =
    PersonDetailUi(
        biography = biography,
        homepage = homePage?.replace("http", "https"),
        id = id,
        name = name,
        popularity = popularity,
        profilePath = buildImageUrl(bigProfilePath = profilePath)
    )