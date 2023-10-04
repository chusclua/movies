package com.chus.clua.data.di

import com.chus.clua.data.datasource.PagerDataSource
import com.chus.clua.data.datasource.PagerDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class PagerModule {
    @Binds
    abstract fun providePagerDataSource(pagerDataSourceImp: PagerDataSourceImp): PagerDataSource
}