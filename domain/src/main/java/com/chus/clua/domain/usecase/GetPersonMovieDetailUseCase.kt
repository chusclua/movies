package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.model.PersonDetail
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class GetPersonMovieDetailUseCase @Inject constructor(
    private val personDataDetailUseCase: GetPersonDataDetailUseCase,
    private val personMovieCreditsUseCase: GetPersonMovieCreditsUseCase,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(personId: Int): PersonDetail = withContext(dispatcherIO) {
        val personDataDetail = async { personDataDetailUseCase(personId) }
        val credits = async { personMovieCreditsUseCase(personId) }
        PersonDetail(
            detail = personDataDetail.await().getOrNull(),
            cast = credits.await().getOrNull()?.cast ?: emptyList(),
            crew = credits.await().getOrNull()?.crew ?: emptyList()
        )
    }
}