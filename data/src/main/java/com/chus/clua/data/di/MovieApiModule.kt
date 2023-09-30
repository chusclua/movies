package com.chus.clua.data.di

import com.chus.clua.data.network.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieApiModule {

    @Provides
    @Singleton
    fun provideNewsApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

}