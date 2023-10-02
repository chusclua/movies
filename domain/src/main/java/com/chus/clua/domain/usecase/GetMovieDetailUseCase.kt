package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.flatMap
import com.chus.clua.domain.model.MovieData
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


@Singleton
class GetMovieDetailUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: Int): Either<Exception, MovieData> =
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