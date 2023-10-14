package com.chus.clua.data.network

import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.data.network.model.MoviesApiModel
import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET(DISCOVER_PATH)
    suspend fun getDiscoverMovies(
        @Query(PAGE_PARAM) page: Int,
        @Query(SORT_PARAM) sort: String = SORT
    ): Either<AppError, MoviesApiModel>

    @GET(TOP_RATED)
    suspend fun getTopRatedMovies(
        @Query(PAGE_PARAM) page: Int
    ): Either<AppError, MoviesApiModel>

    @GET(SEARCH_PATH)
    suspend fun searchMovies(
        @Query(QUERY_PARAM) query: String
    ): Either<AppError, MoviesApiModel>

    @GET(DETAIL_PATH)
    suspend fun movieDetail(
        @Path(MOVIE_ID_PATH) movieId: Int
    ): Either<AppError, MovieDetailApiModel>

    @GET(CREDITS_PATH)
    suspend fun movieCredits(
        @Path(MOVIE_ID_PATH) movieId: Int
    ): Either<AppError, MovieCreditsApiModel>

    @GET(VIDEOS_PATH)
    suspend fun movieVideos(
        @Path(MOVIE_ID_PATH) movieId: Int
    ): Either<AppError, MovieVideosApiModel>

}

private const val DISCOVER_PATH = "/3/discover/movie"
private const val TOP_RATED = "/3/movie/top_rated"
private const val SEARCH_PATH = "/3/search/movie"
private const val DETAIL_PATH = "/3/movie/{movie_id}"
private const val CREDITS_PATH = "/3/movie/{movie_id}/credits"
private const val VIDEOS_PATH = "/3/movie/{movie_id}/videos"

private const val PAGE_PARAM = "page"

private const val SORT_PARAM = "sort_by"
private const val QUERY_PARAM = "query"

private const val MOVIE_ID_PATH = "movie_id"
private const val SORT = "popularity.desc"