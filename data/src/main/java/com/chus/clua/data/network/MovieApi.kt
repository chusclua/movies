package com.chus.clua.data.network

import com.chus.clua.data.network.model.MoviesResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET(DISCOVER_PATH)
    suspend fun getDiscoverMovies(
        @Query(PAGE_PARAM) page: Int,
        @Query(LANGUAGE_PARAM) language: String = LANGUAGE,
        @Query(SORT_PARAM) sort: String = SORT
    ): Response<MoviesResponseModel>

    @GET(SEARCH_PATH)
    suspend fun searchMovies(
        @Query(QUERY_PARAM) query: String,
        @Query(LANGUAGE_PARAM) language: String = LANGUAGE,
    ): Response<MoviesResponseModel>

}

private const val DISCOVER_PATH = "/3/discover/movie"
private const val SEARCH_PATH = "/3/search/movie"

private const val PAGE_PARAM = "page"
private const val LANGUAGE_PARAM = "category"
private const val SORT_PARAM = "sort_by"

private const val QUERY_PARAM = "query"

private const val LANGUAGE = "en-US"
private const val SORT = "popularity.desc"