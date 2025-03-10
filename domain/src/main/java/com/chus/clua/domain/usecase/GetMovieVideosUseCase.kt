package com.chus.clua.domain.usecase

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.map
import com.chus.clua.domain.model.MovieVideos
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class GetMovieVideosUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(movieId: Int): Either<AppError, MovieVideos> =
        withContext(dispatcherIO) {
            repository.getMovieVideos(movieId).map {
                it.copy(
                    videos = it.videos.filter { video ->
                        video.site.equals("youtube", ignoreCase = true)
                    }
                )
            }
        }
}
