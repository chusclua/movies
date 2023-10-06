package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.getOrNull
import com.chus.clua.domain.model.MovieDetail
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


@Singleton
class GetMovieDetailUseCase @Inject constructor(
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val movieDataUseCase: GetMovieDataUseCase,
    private val movieCreditsUseCase: GetMovieCreditsUseCase,
    private val movieVideosUseCase: GetMovieVideosUseCase,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(movieId: Int): MovieDetail = withContext(dispatcherIO) {
        val isFavorite = async { isFavoriteUseCase(movieId) }
        val credits = async { movieCreditsUseCase(movieId) }
        val data = async { movieDataUseCase(movieId) }
        val videos = async { movieVideosUseCase(movieId) }
        MovieDetail(
            isFavorite = isFavorite.await(),
            movieData = data.await().getOrNull(),
            movieCredits = credits.await().getOrNull(),
            movieVideos = videos.await().getOrNull()
        )
    }
}