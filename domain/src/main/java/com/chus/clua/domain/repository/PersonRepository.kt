package com.chus.clua.domain.repository

import com.chus.clua.domain.Either
import com.chus.clua.domain.model.PersonCredits
import com.chus.clua.domain.model.PersonDataDetail


interface PersonRepository {
    suspend fun getPersonDataDetail(personId: Int): Either<Exception, PersonDataDetail>
    suspend fun getPersonMovieCredits(personId: Int): Either<Exception, PersonCredits>
}
