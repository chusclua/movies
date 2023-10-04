package com.chus.clua.domain.repository

import androidx.paging.PagingData
import com.chus.clua.domain.Either
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieData
import com.chus.clua.domain.model.MovieVideos
import kotlinx.coroutines.flow.Flow


interface MoviesRepository {
    fun getDiscoverMovies(): Flow<PagingData<Movie>>
    suspend fun searchMovies(query: String): Either<Exception, List<Movie>>
    suspend fun getMovieDetail(movieId: Int): Either<Exception, MovieData>
    suspend fun getMovieCredits(movieId: Int): Either<Exception, MovieCredits>
    suspend fun getMovieVideos(movieId: Int): Either<Exception, MovieVideos>

    fun saveToFavorites(movieId: Int)
    fun deleteFromFavorite(movieId: Int)
    fun deleteAll()
    fun getFavorites(): Flow<List<Movie>>
    fun isFavorite(movieId: Int) : Boolean
}