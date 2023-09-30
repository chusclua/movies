package com.chus.clua.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.mapper.toMovie
import com.chus.clua.data.paging.MoviesPagingSource
import com.chus.clua.domain.Either
import com.chus.clua.domain.map
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class MoviesRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MoviesRepository {
    override fun getDiscoverMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            MoviesPagingSource(remoteDataSource)
        }
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
}