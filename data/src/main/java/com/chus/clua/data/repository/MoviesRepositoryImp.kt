package com.chus.clua.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.chus.clua.data.datasource.MovieCacheDataSource
import com.chus.clua.data.datasource.MoviePagerDataSource
import com.chus.clua.data.datasource.MovieRemoteDataSource
import com.chus.clua.data.db.MoviesDao
import com.chus.clua.data.mapper.toEntity
import com.chus.clua.data.mapper.toMovie
import com.chus.clua.data.mapper.toMovieCredits
import com.chus.clua.data.mapper.toMovieDataDetail
import com.chus.clua.data.mapper.toMovieVideos
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import com.chus.clua.domain.map
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieDataDetail
import com.chus.clua.domain.model.MovieVideos
import com.chus.clua.domain.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class MoviesRepositoryImp @Inject constructor(
    private val moviePagerDataSource: MoviePagerDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieCacheDataSource: MovieCacheDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override fun getDiscoverMovies(): Flow<PagingData<Movie>> =
        moviePagerDataSource.getMoviePage().map { pagingData ->
            pagingData.map { model ->
                model.toMovie().also { movie ->
                    movieCacheDataSource.addMovie(movie)
                }
            }
        }

    override fun getTopRatedMovies(): Flow<PagingData<Movie>> =
        moviePagerDataSource.getTopRatedMoviePage().map { pagingData ->
            pagingData.map { model ->
                model.toMovie().also { movie ->
                    movieCacheDataSource.addMovie(movie)
                }
            }
        }

    override suspend fun searchMovies(query: String): Either<AppError, List<Movie>> {
        return movieRemoteDataSource.searchMovies(query).map { response ->
            response.results.map { model ->
                model.toMovie().also { movie ->
                    movieCacheDataSource.addMovie(movie)
                }
            }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Either<AppError, MovieDataDetail> {
        return movieRemoteDataSource.getMovieDetail(movieId = movieId).map { response ->
            response.toMovieDataDetail()
        }
    }

    override suspend fun getMovieCredits(movieId: Int): Either<AppError, MovieCredits> {
        return movieRemoteDataSource.getMovieCredits(movieId = movieId).map { response ->
            response.toMovieCredits()
        }
    }

    override suspend fun getMovieVideos(movieId: Int): Either<AppError, MovieVideos> {
        return movieRemoteDataSource.getMovieVideos(movieId = movieId).map { response ->
            response.toMovieVideos()
        }
    }

    override fun saveToFavorites(movieId: Int) {
        movieCacheDataSource.getMovie(movieId)?.let { movie ->
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