package com.chus.clua.data.repository

import com.chus.clua.data.datasource.PersonRemoteDataSource
import com.chus.clua.data.mapper.toPersonCredits
import com.chus.clua.data.mapper.toPersonDetail
import com.chus.clua.domain.Either
import com.chus.clua.domain.map
import com.chus.clua.domain.model.PersonCredits
import com.chus.clua.domain.model.PersonDataDetail
import com.chus.clua.domain.repository.PersonRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonRepositoryImp @Inject constructor(private val dataSource: PersonRemoteDataSource): PersonRepository {

    override suspend fun getPersonDataDetail(personId: Int): Either<Exception, PersonDataDetail> {
        return dataSource.getPersonDataDetail(personId = personId).map { apiModel ->
            apiModel.toPersonDetail()
        }
    }

    override suspend fun getPersonMovieCredits(personId: Int): Either<Exception, PersonCredits> {
        return dataSource.getPersonMovieCredits(personId = personId).map { apiModel ->
            apiModel.toPersonCredits()
        }
    }
}