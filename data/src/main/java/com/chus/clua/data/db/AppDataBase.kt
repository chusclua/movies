package com.chus.clua.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chus.clua.data.db.converter.IdsConverter
import com.chus.clua.data.db.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
@TypeConverters(IdsConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

}