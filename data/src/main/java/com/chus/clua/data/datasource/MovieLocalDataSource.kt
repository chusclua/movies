package com.chus.clua.data.datasource

import com.chus.clua.data.db.MoviesDao
import com.chus.clua.data.db.entity.MovieEntity
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow


interface MovieLocalDataSource {
    fun getAllMovies(): Flow<List<MovieEntity>>
    fun insertMovie(movie: MovieEntity)
    fun deleteMovie(id: Int)
    fun deleteAllMovies()
    fun containsMovie(id: Int): Boolean
}

@Singleton
class MovieLocalDataSourceImp @Inject constructor(
    private val moviesDao: MoviesDao
) : MovieLocalDataSource {
    override fun getAllMovies(): Flow<List<MovieEntity>> {
        return moviesDao.getAll()
    }

    override fun insertMovie(movie: MovieEntity) {
        moviesDao.insert(movie)
    }

    override fun deleteMovie(id: Int) {
        moviesDao.delete(id)
    }

    override fun deleteAllMovies() {
        moviesDao.deleteAll()
    }

    override fun containsMovie(id: Int): Boolean {
        return moviesDao.contains(id)
    }
}