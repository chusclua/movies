package com.chus.clua.domain.usecase

import androidx.paging.PagingData
import androidx.paging.filter
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.repository.MoviesRepository
import dagger.Reusable
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

@Reusable
class GetDiscoverMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    operator fun invoke(): Flow<PagingData<Movie>> =
        repository.getDiscoverMovies().flowOn(dispatcherIO).map { pagingData ->
            pagingData.filter { movie ->
                !movie.backdropPath.isNullOrEmpty() || !movie.posterPath.isNullOrEmpty()
            }
        }
}