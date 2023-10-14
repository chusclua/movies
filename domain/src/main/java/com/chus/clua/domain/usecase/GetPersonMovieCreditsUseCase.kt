package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.map
import com.chus.clua.domain.model.PersonCredits
import com.chus.clua.domain.repository.PersonRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class GetPersonMovieCreditsUseCase @Inject constructor(
    private val repository: PersonRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(personId: Int): Either<Exception, PersonCredits> =
        withContext(dispatcherIO) {
            repository.getPersonMovieCredits(personId = personId).map { personCredits ->
                personCredits.copy(
                    cast = personCredits.cast.filter { it.posterPath != null },
                    crew = personCredits.crew.filter { it.posterPath != null }
                )
            }
        }
}