package com.chus.clua.data.network

import com.chus.clua.data.network.model.MovieCreditsResponse
import com.chus.clua.data.network.model.MovieDetailResponse
import com.chus.clua.data.network.model.MovieVideosResponse
import com.chus.clua.data.network.model.MoviesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET(DISCOVER_PATH)
    suspend fun getDiscoverMovies(
        @Query(PAGE_PARAM) page: Int,
        @Query(SORT_PARAM) sort: String = SORT
    ): Response<MoviesResponseModel>

    @GET(SEARCH_PATH)
    suspend fun searchMovies(
        @Query(QUERY_PARAM) query: String
    ): Response<MoviesResponseModel>

    @GET(DETAIL_PATH)
    suspend fun movieDetail(
        @Path(MOVIE_ID_PATH) movieId: Int
    ): Response<MovieDetailResponse>

    @GET(CREDITS_PATH)
    suspend fun movieCredits(
        @Path(MOVIE_ID_PATH) movieId: Int
    ): Response<MovieCreditsResponse>

    @GET(VIDEOS_PATH)
    suspend fun movieVideos(
        @Path(MOVIE_ID_PATH) movieId: Int
    ): Response<MovieVideosResponse>

}

private const val DISCOVER_PATH = "/3/discover/movie"
private const val SEARCH_PATH = "/3/search/movie"
private const val DETAIL_PATH = "/3/movie/{movie_id}"
private const val CREDITS_PATH = "/3/movie/{movie_id}/credits"
private const val VIDEOS_PATH = "/3/movie/{movie_id}/videos"

private const val PAGE_PARAM = "page"

private const val SORT_PARAM = "sort_by"
private const val QUERY_PARAM = "query"

private const val MOVIE_ID_PATH = "movie_id"
private const val SORT = "popularity.desc"