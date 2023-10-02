package com.chus.clua.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.mapper.toMovie
import com.chus.clua.data.mapper.toMovieCredits
import com.chus.clua.data.mapper.toMovieDetail
import com.chus.clua.data.paging.MoviesPagingSource
import com.chus.clua.domain.Either
import com.chus.clua.domain.map
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieData
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class MoviesRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val pagingSource: MoviesPagingSource
) : MoviesRepository {
    override fun getDiscoverMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pagingSource }
    ).flow.map { pagingData ->
        pagingData.map { model ->
            model.toMovie()
        }
    }

    override suspend fun searchMovies(query: String): Either<Exception, List<Movie>> {
        return remoteDataSource.searchMovies(query).map { response ->
            response.results.map { model -> model.toMovie() }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Either<Exception, MovieData> {
        return remoteDataSource.getMovieDetail(movieId = movieId).map { response ->
            response.toMovieDetail()
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Either<Exception, MovieCredits> {
        return remoteDataSource.getMovieCredits(movieId = movieId).map { response ->
            response.toMovieCredits()
        }
    }
}