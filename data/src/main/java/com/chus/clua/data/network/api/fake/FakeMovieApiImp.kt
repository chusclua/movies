package com.chus.clua.data.network.api.fake

import com.chus.clua.data.network.api.MovieApi
import com.chus.clua.data.network.model.MovieApiModel
import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.data.network.model.MoviesApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FakeMovieApiImp @Inject constructor() : BaseFakeApi(), MovieApi {

    private val MovieApiList: List<MovieApiModel> get() = (1..20).map { MovieApi.copy(id = it) }

    private val MovieApi = MovieApiModel(
        adult = false,
        backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
        genreIds = listOf(18, 80),
        id = 238,
        originalLanguage = "en",
        originalTitle = "The Godfather",
        overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
        popularity = 117.613,
        posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
        releaseDate = "1972-03-14",
        title = "The Godfather",
        video = false,
        voteAverage = 8.709,
        voteCount = 18755
    )

    private val MoviesApi = MoviesApiModel(
        page = 1,
        results = MovieApiList,
        totalPages = 40514,
        totalResults = 810266
    )

    override suspend fun getDiscoverMovies(
        page: Int,
        sort: String
    ): Either<AppError, MoviesApiModel> = when (isLeft) {
        false -> Either.Right(MoviesApi)
        else -> Either.Left(AppError.HttpError(404, "not found"))
    }

    override suspend fun getTopRatedMovies(page: Int): Either<AppError, MoviesApiModel> =
        when (isLeft) {
            false -> Either.Right(MoviesApi)
            else -> Either.Left(AppError.HttpError(404, "not found"))
        }

    override suspend fun searchMovies(query: String): Either<AppError, MoviesApiModel> =
        when (isLeft) {
            false -> Either.Right(MoviesApi)
            else -> Either.Left(AppError.HttpError(404, "not found"))
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