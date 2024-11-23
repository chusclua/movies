package com.chus.clua.data.di

import com.chus.clua.data.repository.MoviesRepositoryImp
import com.chus.clua.data.repository.PersonRepositoryImp
import com.chus.clua.domain.repository.MoviesRepository
import com.chus.clua.domain.repository.PersonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(repositoryImp: MoviesRepositoryImp): MoviesRepository

    @Binds
    abstract fun providePersonRepository(repositoryImp: PersonRepositoryImp): PersonRepository
}
