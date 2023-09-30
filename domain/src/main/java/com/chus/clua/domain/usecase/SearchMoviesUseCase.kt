package com.chus.clua.domain.usecase

import com.chus.clua.domain.Either
import com.chus.clua.domain.IoDispatcher
import com.chus.clua.domain.map
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@Singleton
class SearchMoviesUseCase @Inject constructor(
    private val repository: MoviesRepository,
    @IoDispatcher
    private val dispatcherIO: CoroutineDispatcher
) {
    suspend operator fun invoke(query: String): Either<Exception, List<Movie>> =
        withContext(dispatcherIO) {
            repository.searchMovies(query).map { list ->
                list.filter { movie ->
                    !movie.backdropPath.isNullOrEmpty() || !movie.posterPath.isNullOrEmpty()
                }
            }
        }

}