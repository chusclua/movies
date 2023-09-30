package com.chus.clua.data.datasource

import com.chus.clua.data.network.MovieApi
import com.chus.clua.data.network.model.MoviesResponseModel
import com.chus.clua.data.serviceHandler
import com.chus.clua.domain.Either
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val movieApi: MovieApi) {

    suspend fun getDiscoverMovies(page: Int): Either<Exception, MoviesResponseModel> =
        serviceHandler {
            movieApi.getDiscoverMovies(page = page)
        }

    suspend fun searchMovies(query: String): Either<Exception, MoviesResponseModel> =
        serviceHandler {
            movieApi.searchMovies(query = query)
        }


}