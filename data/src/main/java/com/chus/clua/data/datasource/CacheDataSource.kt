package com.chus.clua.data.datasource

import com.chus.clua.domain.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CacheDataSource @Inject constructor(
    private val cache: MutableMap<Int, Movie>
) {
    fun addMovie(movie: Movie) {
        cache[movie.id] = movie
    }

    fun getMovie(id: Int): Movie? = cache[id]
}