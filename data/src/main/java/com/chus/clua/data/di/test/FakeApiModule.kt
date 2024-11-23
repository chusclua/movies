package com.chus.clua.data.di.test

import com.chus.clua.data.di.ApiModule
import com.chus.clua.data.network.api.MovieApi
import com.chus.clua.data.network.api.PersonApi
import com.chus.clua.data.network.api.fake.FakeMovieApiImp
import com.chus.clua.data.network.api.fake.FakePersonApiImp
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiModule::class],
)
abstract class FakeApiModule {
    @Binds
    @Singleton
    abstract fun providePersonApi(fakePersonApiImp: FakePersonApiImp): PersonApi

    @Binds
    @Singleton
    abstract fun provideMovieApi(fakeMovieApiImp: FakeMovieApiImp): MovieApi
}
