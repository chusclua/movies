package com.chus.clua.data.di

import com.chus.clua.data.datasource.NetworkDataSource
import com.chus.clua.data.datasource.NetworkDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkDataSourceModule {
    @Binds
    abstract fun provideNetworkDataSource(dataSource: NetworkDataSourceImp): NetworkDataSource
}
