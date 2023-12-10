package com.chus.clua.data.di

import com.chus.clua.data.datasource.MovieLocalDataSource
import com.chus.clua.data.datasource.MovieLocalDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieLocalDataSourceModule {
    @Binds
    abstract fun provideMovieLocalDataSource(dataSource: MovieLocalDataSourceImp): MovieLocalDataSource
}