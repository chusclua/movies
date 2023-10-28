package com.chus.clua.data.network.api.fake

import com.chus.clua.data.network.api.MovieApi
import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.data.network.model.MoviesApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FakeMovieApiImp @Inject constructor(): MovieApi{
    override suspend fun getDiscoverMovies(
        page: Int,
        sort: String
    ): Either<AppError, MoviesApiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun getTopRatedMovies(page: Int): Either<AppError, MoviesApiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun searchMovies(query: String): Either<AppError, MoviesApiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun movieDetail(movieId: Int): Either<AppError, MovieDetailApiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun movieCredits(movieId: Int): Either<AppError, MovieCreditsApiModel> {
        TODO("Not yet implemented")
    }

    override suspend fun movieVideos(movieId: Int): Either<AppError, MovieVideosApiModel> {
        TODO("Not yet implemented")
    }
}