package com.chus.clua.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chus.clua.data.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movie")
    fun getAll(): Flow<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieEntity)

    @Query("DELETE FROM movie WHERE id = :id")
    fun delete(id: Int)

    @Query("DELETE FROM movie")
    fun deleteAll()

    @Query("SELECT count(*) from movie WHERE id LIKE :id")
    fun contains(id: Int): Boolean

}