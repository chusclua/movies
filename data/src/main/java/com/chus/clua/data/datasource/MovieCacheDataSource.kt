package com.chus.clua.data.datasource

import com.chus.clua.domain.model.Movie
import javax.inject.Inject
import javax.inject.Singleton

interface MovieCacheDataSource {
    fun addMovie(movie: Movie)
    fun getMovie(id: Int): Movie?
}

@Singleton
class MovieCacheDataSourceImp @Inject constructor(
    private val cache: MutableMap<Int, Movie>
) : MovieCacheDataSource {
    override fun addMovie(movie: Movie) {
        cache[movie.id] = movie
    }

    override fun getMovie(id: Int): Movie? = cache[id]
}