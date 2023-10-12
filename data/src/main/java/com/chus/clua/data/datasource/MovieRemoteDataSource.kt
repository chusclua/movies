package com.chus.clua.data.datasource

import com.chus.clua.data.network.MovieApi
import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.data.network.model.MoviesApiModel
import com.chus.clua.data.serviceHandler
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton

interface MovieRemoteDataSource {
    suspend fun getDiscoverMovies(page: Int): Either<Exception, MoviesApiModel>
    suspend fun getTopRatedMovies(page: Int): Either<Exception, MoviesApiModel>
    suspend fun searchMovies(query: String): Either<Exception, MoviesApiModel>
    suspend fun getMovieDetail(movieId: Int): Either<Exception, MovieDetailApiModel>
    suspend fun getMovieCredits(movieId: Int): Either<Exception, MovieCreditsApiModel>
    suspend fun getMovieVideos(movieId: Int): Either<Exception, MovieVideosApiModel>
}

@Singleton
class MovieRemoteDataSourceImp @Inject constructor(private val movieApi: MovieApi) : MovieRemoteDataSource {

    override suspend fun getDiscoverMovies(page: Int): Either<Exception, MoviesApiModel> =
        serviceHandler {
            movieApi.getDiscoverMovies(page = page)
        }

    override suspend fun getTopRatedMovies(page: Int): Either<Exception, MoviesApiModel> =
        serviceHandler {
            movieApi.getTopRatedMovies(page = page)
        }

    override suspend fun searchMovies(query: String): Either<Exception, MoviesApiModel> =
        serviceHandler {
            movieApi.searchMovies(query = query)
        }

    override suspend fun getMovieDetail(movieId: Int): Either<Exception, MovieDetailApiModel> =
        serviceHandler {
            movieApi.movieDetail(movieId = movieId)
        }

    override suspend fun getMovieCredits(movieId: Int): Either<Exception, MovieCreditsApiModel> =
        serviceHandler {
            movieApi.movieCredits(movieId = movieId)
        }

    override suspend fun getMovieVideos(movieId: Int): Either<Exception, MovieVideosApiModel> =
        serviceHandler {
            movieApi.movieVideos(movieId = movieId)
        }


}