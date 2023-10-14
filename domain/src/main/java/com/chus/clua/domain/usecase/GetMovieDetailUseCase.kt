package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.model.MovieDetail
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


@Reusable
class GetMovieDetailUseCase @Inject constructor(
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val movieDataUseCase: GetMovieDataUseCase,
    private val movieCreditsUseCase: GetMovieCreditsUseCase,
    private val movieVideosUseCase: GetMovieVideosUseCase,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(movieId: Int): Either<Exception, MovieDetail> =
        withContext(dispatcherIO) {
            val isFavorite = async { isFavoriteUseCase(movieId) }
            val credits = async { movieCreditsUseCase(movieId) }
            val data = async { movieDataUseCase(movieId) }
            val videos = async { movieVideosUseCase(movieId) }
            data.await().getOrNull()?.let { data ->
                Either.Right(
                    MovieDetail(
                        isFavorite = isFavorite.await(),
                        movieData = data,
                        cast =  credits.await().getOrNull()?.cast.orEmpty(),
                        crew =  credits.await().getOrNull()?.crew.orEmpty(),
                        videos = videos.await().getOrNull()?.videos.orEmpty()
                    )
                )
            } ?: run {
                Either.Left(Exception())
            }
        }
}