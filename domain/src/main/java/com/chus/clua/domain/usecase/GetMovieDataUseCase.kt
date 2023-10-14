package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.flatMap
import com.chus.clua.domain.model.MovieDataDetail
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


@Reusable
class GetMovieDataUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: Int): Either<Exception, MovieDataDetail> =
        withContext(dispatcherIO) {
            repository.getMovieDetail(movieId).flatMap { movieData ->
                if (movieData.backdropPath == null || movieData.posterPath == null) {
                    Either.Left(Exception())
                } else {
                    Either.Right(movieData)
                }
            }
        }
}