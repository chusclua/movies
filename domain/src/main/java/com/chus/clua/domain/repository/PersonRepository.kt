package com.chus.clua.domain.repository

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.model.PersonCredits
import com.chus.clua.domain.model.PersonDataDetail


interface PersonRepository {
    suspend fun getPersonDataDetail(personId: Int): Either<AppError, PersonDataDetail>
    suspend fun getPersonMovieCredits(personId: Int): Either<AppError, PersonCredits>
}
