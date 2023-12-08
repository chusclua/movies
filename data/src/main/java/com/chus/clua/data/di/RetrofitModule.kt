package com.chus.clua.data.di

import com.chus.clua.data.BuildConfig
import com.chus.clua.data.datasource.NetworkCacheDatasource
import com.chus.clua.data.network.interceptors.RequestInterceptor
import com.chus.clua.data.network.adapter.EitherCallAdapterFactory
import com.chus.clua.data.network.interceptors.CacheInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        callAdapterFactory: EitherCallAdapterFactory
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(callAdapterFactory)
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        requestInterceptor: RequestInterceptor,
        cacheInterceptor: CacheInterceptor,
        cacheDatasource: NetworkCacheDatasource,
        loggingInterceptor: Interceptor
    ): OkHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .cache(cacheDatasource.getCache())
            .addInterceptor(loggingInterceptor)
            .addInterceptor(requestInterceptor)
            .addInterceptor(cacheInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

}

private const val READ_TIMEOUT = 10L
private const val WRITE_TIMEOUT = 60L