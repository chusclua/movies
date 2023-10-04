package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext


@Singleton
class ToggleFavoriteMovieUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend operator fun invoke(movieId: Int, isFavorite: Boolean) =
        withContext(dispatcherIO) {
            if (isFavorite) {
                repository.deleteFromFavorite(movieId)
            } else {
                repository.saveToFavorites(movieId)
            }
        }
}