package com.chus.clua.data.datasource

import com.chus.clua.data.network.PersonApi
import com.chus.clua.data.network.model.PersonDetailApiModel
import com.chus.clua.data.network.model.PersonMovieCreditsApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton


interface PersonRemoteDataSource {
    suspend fun getPersonDataDetail(personId: Int): Either<AppError, PersonDetailApiModel>
    suspend fun getPersonMovieCredits(personId: Int): Either<AppError, PersonMovieCreditsApiModel>
}

@Singleton
class PersonRemoteDataSourceImp @Inject constructor(
    private val personApi: PersonApi
) : PersonRemoteDataSource {

    override suspend fun getPersonDataDetail(personId: Int): Either<AppError, PersonDetailApiModel> =
        personApi.getPersonDetail(personId = personId)

    override suspend fun getPersonMovieCredits(personId: Int): Either<AppError, PersonMovieCreditsApiModel> =
        personApi.getPersonMovieCredits(personId = personId)

}