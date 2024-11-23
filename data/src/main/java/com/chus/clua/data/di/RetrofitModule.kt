package com.chus.clua.data.di

import com.chus.clua.data.BuildConfig
import com.chus.clua.data.datasource.NetworkCacheDatasource
import com.chus.clua.data.network.adapter.EitherCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {
    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        callAdapterFactory: EitherCallAdapterFactory,
    ): Retrofit =
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        @Named(InterceptorModule.REQUEST) requestInterceptor: Interceptor,
        @Named(InterceptorModule.CACHE) cacheInterceptor: Interceptor,
        @Named(InterceptorModule.LOGGING) loggingInterceptor: Interceptor,
        cacheDatasource: NetworkCacheDatasource,
    ): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .cache(cacheDatasource.getCache())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .addInterceptor(cacheInterceptor)
            .build()
}

private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 60L
