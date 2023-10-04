package com.chus.clua.data.di

import com.chus.clua.data.datasource.CacheDataSource
import com.chus.clua.data.datasource.CacheDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class CacheModule {
    @Binds
    abstract fun provideCacheDataSource(dataSource: CacheDataSourceImp): CacheDataSource
}