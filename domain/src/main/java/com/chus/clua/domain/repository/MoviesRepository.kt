package com.chus.clua.domain.repository

import androidx.paging.PagingData
import com.chus.clua.domain.Either
import com.chus.clua.domain.model.Movie
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    fun getDiscoverMovies(): Flow<PagingData<Movie>>
    suspend fun searchMovies(query: String): Either<Exception, List<Movie>>
}