package com.chus.clua.data.di

import com.chus.clua.data.datasource.MoviePagerDataSource
import com.chus.clua.data.datasource.MoviePagerDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class MoviePagerDataSourceModule {
    @Binds
    abstract fun provideMoviePagerDataSource(pagerDataSourceImp: MoviePagerDataSourceImp): MoviePagerDataSource
}