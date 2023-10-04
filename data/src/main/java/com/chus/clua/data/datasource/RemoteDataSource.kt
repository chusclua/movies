package com.chus.clua.data.datasource

import com.chus.clua.data.network.MovieApi
import com.chus.clua.data.network.model.MovieCreditsResponse
import com.chus.clua.data.network.model.MovieDetailResponse
import com.chus.clua.data.network.model.MovieVideosResponse
import com.chus.clua.data.network.model.MoviesResponseModel
import com.chus.clua.data.serviceHandler
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton

interface RemoteDataSource {
    suspend fun getDiscoverMovies(page: Int): Either<Exception, MoviesResponseModel>
    suspend fun searchMovies(query: String): Either<Exception, MoviesResponseModel>
    suspend fun getMovieDetail(movieId: Int): Either<Exception, MovieDetailResponse>
    suspend fun getMovieCredits(movieId: Int): Either<Exception, MovieCreditsResponse>
    suspend fun getMovieVideos(movieId: Int): Either<Exception, MovieVideosResponse>
}

@Singleton
class RemoteDataSourceImp @Inject constructor(private val movieApi: MovieApi) : RemoteDataSource {

    override suspend fun getDiscoverMovies(page: Int): Either<Exception, MoviesResponseModel> =
        serviceHandler {
            movieApi.getDiscoverMovies(page = page)
        }

    override suspend fun searchMovies(query: String): Either<Exception, MoviesResponseModel> =
        serviceHandler {
            movieApi.searchMovies(query = query)
        }

    override suspend fun getMovieDetail(movieId: Int): Either<Exception, MovieDetailResponse> =
        serviceHandler {
            movieApi.movieDetail(movieId = movieId)
        }

    override suspend fun getMovieCredits(movieId: Int): Either<Exception, MovieCreditsResponse> =
        serviceHandler {
            movieApi.movieCredits(movieId = movieId)
        }

    override suspend fun getMovieVideos(movieId: Int): Either<Exception, MovieVideosResponse> =
        serviceHandler {
            movieApi.movieVideos(movieId = movieId)
        }


}