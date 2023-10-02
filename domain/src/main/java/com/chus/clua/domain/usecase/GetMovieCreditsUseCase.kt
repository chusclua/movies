package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.map
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


@Singleton
class GetMovieCreditsUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: Int): Either<Exception, MovieCredits> =
        withContext(dispatcherIO) {
            repository.getMovieCredits(movieId).map { credits ->
                credits.copy(
                    cast = credits.cast.filter { it.profilePath != null },
                    crew = credits.crew.filter { it.profilePath != null }.sortedBy {
                        !it.job.equals("director", ignoreCase = true)
                    }
                )
            }
        }
}