package com.chus.clua.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.chus.clua.data.datasource.CacheDataSource
import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.db.MoviesDao
import com.chus.clua.data.mapper.toEntity
import com.chus.clua.data.mapper.toMovie
import com.chus.clua.data.mapper.toMovieCredits
import com.chus.clua.data.mapper.toMovieDetail
import com.chus.clua.data.mapper.toMovieVideos
import com.chus.clua.data.paging.MoviesPagingSource
import com.chus.clua.domain.Either
import com.chus.clua.domain.map
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieData
import com.chus.clua.domain.model.MovieVideos
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class MoviesRepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val pagingSource: MoviesPagingSource,
    private val cacheDataSource: CacheDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override fun getDiscoverMovies(): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { pagingSource }
    ).flow.map { pagingData ->
        pagingData.map { model ->
            model.toMovie().also { movie ->
                cacheDataSource.addMovie(movie)
            }
        }
    }

    override suspend fun searchMovies(query: String): Either<Exception, List<Movie>> {
        return remoteDataSource.searchMovies(query).map { response ->
            response.results.map { model ->
                model.toMovie().also { movie ->
                    cacheDataSource.addMovie(movie)
                }
            }
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

    override suspend fun getMovieVideos(movieId: Int): Either<Exception, MovieVideos> {
        return remoteDataSource.getMovieVideos(movieId = movieId).map { response ->
            response.toMovieVideos()
        }
    }

    override fun saveToFavorites(movieId: Int) {
        cacheDataSource.getMovie(movieId)?.let { movie ->
            moviesDao.insert(movie.toEntity())
        }
    }

    override fun deleteFromFavorite(movieId: Int) {
        moviesDao.delete(movieId)
    }

    override fun deleteAll() {
        moviesDao.deleteAll()
    }

    override fun getFavorites(): Flow<List<Movie>> {
        return moviesDao.getAll().map { entities ->
            entities.map { entity ->
                entity.toMovie()
            }
        }
    }

    override fun isFavorite(movieId: Int): Boolean {
        return moviesDao.contains(movieId)
    }
}