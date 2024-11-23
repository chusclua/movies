package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Reusable
class ToggleFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: Int, isFavorite: Boolean): Unit =
        withContext(dispatcherIO) {
            if (isFavorite) {
                repository.deleteFromFavorite(movieId)
            } else {
                repository.saveToFavorites(movieId)
            }
        }
}
