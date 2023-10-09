package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.PersonDetailApiModel
import com.chus.clua.domain.model.Gender
import com.chus.clua.domain.model.PersonDataDetail


fun PersonDetailApiModel.toPersonDetail() =
    PersonDataDetail(
        adult = adult,
        alsoKnownAs = alsoKnownAs,
        biography = biography,
        birthday = birthday,
        deathDay = deathDay,
        gender = Gender.fromInt(gender),
        homePage = homepage,
        id = id,
        imdbId = imdbId,
        knownForDepartment = knownForDepartment,
        name = name,
        placeOfBirth = placeOfBirth,
        popularity = popularity,
        profilePath = profilePath
    )