package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.map
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


@Reusable
class GetMovieCreditsUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: Int): Either<AppError, MovieCredits> =
        withContext(dispatcherIO) {
            repository.getMovieCredits(movieId).map { credits ->
                credits.copy(
                    cast = credits.cast.filter { !it.profilePath.isNullOrEmpty() },
                    crew = credits.crew.filter { !it.profilePath.isNullOrEmpty() }.sortedBy {
                        !it.job.equals("director", ignoreCase = true)
                    }
                )
            }
        }
}