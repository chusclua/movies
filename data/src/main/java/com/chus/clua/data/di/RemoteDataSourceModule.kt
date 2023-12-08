package com.chus.clua.data.di

import com.chus.clua.data.datasource.MovieRemoteDataSource
import com.chus.clua.data.datasource.MovieRemoteDataSourceImp
import com.chus.clua.data.datasource.NetworkCacheDatasource
import com.chus.clua.data.datasource.NetworkCacheDatasourceImp
import com.chus.clua.data.datasource.PersonRemoteDataSource
import com.chus.clua.data.datasource.PersonRemoteDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun provideMovieRemoteDataSource(dataSource: MovieRemoteDataSourceImp): MovieRemoteDataSource

    @Binds
    abstract fun providePersonRemoteDaraSource(dataSource: PersonRemoteDataSourceImp): PersonRemoteDataSource

    @Binds
    abstract fun provideNetworkCacheDataSource(datasource: NetworkCacheDatasourceImp): NetworkCacheDatasource
}