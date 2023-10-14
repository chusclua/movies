package com.chus.clua.data.di

import com.chus.clua.data.network.api.MovieApi
import com.chus.clua.data.network.api.PersonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)

    @Provides
    @Singleton
    fun providePersonApi(retrofit: Retrofit): PersonApi = retrofit.create(PersonApi::class.java)

}