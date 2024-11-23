package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.flatMap
import com.chus.clua.domain.model.PersonDataDetail
import com.chus.clua.domain.repository.PersonRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class GetPersonDataDetailUseCase @Inject constructor(
    private val repository: PersonRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(personId: Int): Either<AppError, PersonDataDetail> =
        withContext(dispatcherIO) {
            repository.getPersonDataDetail(personId = personId).flatMap { personDetail ->
                personDetail.profilePath?.let {
                    Either.Right(personDetail)
                } ?: Either.Left(AppError.InsufficientData("profilePath path null"))
            }
        }
}
