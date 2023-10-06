package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.map
import com.chus.clua.domain.model.MovieVideos
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


@Singleton
class GetMovieVideosUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(movieId: Int): Either<Exception, MovieVideos> =
        withContext(dispatcherIO) {
            repository.getMovieVideos(movieId).map {
                it.copy(videos = it.videos.filter { video ->
                    video.site.equals("youtube", ignoreCase = true)
                })
            }
        }
}