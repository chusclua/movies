package com.chus.clua.data.datasource

import com.chus.clua.data.network.api.MovieApi
import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.data.network.model.MoviesApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRemoteDataSource {
    suspend fun getDiscoverMovies(page: Int): Either<AppError, MoviesApiModel>

    suspend fun getTopRatedMovies(page: Int): Either<AppError, MoviesApiModel>

    suspend fun searchMovies(query: String): Either<AppError, MoviesApiModel>

    suspend fun getMovieDetail(movieId: Int): Either<AppError, MovieDetailApiModel>

    suspend fun getMovieCredits(movieId: Int): Either<AppError, MovieCreditsApiModel>

    suspend fun getMovieVideos(movieId: Int): Either<AppError, MovieVideosApiModel>
}

@Singleton
class MovieRemoteDataSourceImp
    @Inject
    constructor(
        private val movieApi: MovieApi,
    ) : MovieRemoteDataSource {
        override suspend fun getDiscoverMovies(page: Int): Either<AppError, MoviesApiModel> = movieApi.getDiscoverMovies(page = page)

        override suspend fun getTopRatedMovies(page: Int): Either<AppError, MoviesApiModel> = movieApi.getTopRatedMovies(page = page)

        override suspend fun searchMovies(query: String): Either<AppError, MoviesApiModel> = movieApi.searchMovies(query = query)

        override suspend fun getMovieDetail(movieId: Int): Either<AppError, MovieDetailApiModel> = movieApi.movieDetail(movieId = movieId)

        override suspend fun getMovieCredits(movieId: Int): Either<AppError, MovieCreditsApiModel> =
            movieApi.movieCredits(movieId = movieId)

        override suspend fun getMovieVideos(movieId: Int): Either<AppError, MovieVideosApiModel> = movieApi.movieVideos(movieId = movieId)
    }
