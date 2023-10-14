package com.chus.clua.data.di

import com.chus.clua.domain.model.Movie
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class MapModule {

    @Provides
    fun provideMoviesMap(): MutableMap<Int, Movie> = mutableMapOf()

}