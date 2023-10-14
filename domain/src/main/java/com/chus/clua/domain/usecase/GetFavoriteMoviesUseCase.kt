package com.chus.clua.domain.usecase

import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

@Reusable
class GetFavoriteMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher private val dispatcherIO: CoroutineDispatcher
) {
    operator fun invoke(): Flow<List<Movie>> = repository.getFavorites().flowOn(dispatcherIO)
}