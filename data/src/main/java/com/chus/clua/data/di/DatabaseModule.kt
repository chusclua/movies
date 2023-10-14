package com.chus.clua.data.di

import android.content.Context
import androidx.room.Room
import com.chus.clua.data.db.AppDataBase
import com.chus.clua.data.db.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideMoviesDao(appDatabase: AppDataBase): MoviesDao {
        return appDatabase.moviesDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app.favorite.movies"
        ).build()
    }
}