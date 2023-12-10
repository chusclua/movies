package com.chus.clua.data.di

import com.chus.clua.data.datasource.MovieCacheDataSource
import com.chus.clua.data.datasource.MovieCacheDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class MovieCacheDataSourceModule {
    @Binds
    abstract fun provideMovieCacheDataSource(dataSource: MovieCacheDataSourceImp): MovieCacheDataSource
}