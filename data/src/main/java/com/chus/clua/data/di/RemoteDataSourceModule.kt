package com.chus.clua.data.di

import com.chus.clua.data.datasource.RemoteDataSource
import com.chus.clua.data.datasource.RemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun provideRemoteDataSource(dataSource: RemoteDataSourceImp): RemoteDataSource
}