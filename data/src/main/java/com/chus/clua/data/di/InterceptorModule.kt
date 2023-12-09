package com.chus.clua.data.di

import com.chus.clua.data.datasource.NetworkDataSource
import com.chus.clua.data.network.interceptors.CacheInterceptor
import com.chus.clua.data.network.interceptors.RequestInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor


@Module
@InstallIn(SingletonComponent::class)
class InterceptorModule {

    @Provides
    @Singleton
    @Named(LOGGING)
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @Named(CACHE)
    fun provideCacheInterceptor(networkDataSource: NetworkDataSource): Interceptor =
        CacheInterceptor(networkDataSource)

    @Provides
    @Singleton
    @Named(REQUEST)
    fun provideRequestInterceptor(): Interceptor = RequestInterceptor()

    companion object {
        const val LOGGING = "HttpLoggingInterceptor"
        const val CACHE = "CacheInterceptor"
        const val REQUEST = "RequestInterceptor"
    }

}