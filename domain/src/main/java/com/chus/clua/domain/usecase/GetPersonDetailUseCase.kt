package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.model.PersonDetail
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class GetPersonDetailUseCase @Inject constructor(
    private val personDataDetailUseCase: GetPersonDataDetailUseCase,
    private val personMovieCreditsUseCase: GetPersonMovieCreditsUseCase,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(personId: Int): Either<Exception, PersonDetail> =
        withContext(dispatcherIO) {
            val personDataDetail = async { personDataDetailUseCase(personId) }
            val credits = async { personMovieCreditsUseCase(personId) }
            personDataDetail.await().getOrNull()?.let { data ->
                Either.Right(
                    PersonDetail(
                        detail = data,
                        cast = credits.await().getOrNull()?.cast ?: emptyList(),
                        crew = credits.await().getOrNull()?.crew ?: emptyList()
                    )
                )
            } ?: run {
                Either.Left(Exception())
            }
        }
}