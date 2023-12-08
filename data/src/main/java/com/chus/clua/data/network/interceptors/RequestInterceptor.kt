package com.chus.clua.data.network.interceptors

import com.chus.clua.data.BuildConfig
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import okhttp3.Interceptor
import okhttp3.Response


@Singleton
class RequestInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().url.newBuilder().apply {
            addQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY)
            addQueryParameter(LANGUAGE_PARAM, Locale.getDefault().language)
        } .run {
            val requestBuilder = chain.request().newBuilder().url(this.build())
            return chain.proceed(requestBuilder.build())
        }
    }
}

private const val API_KEY_PARAM = "api_key"
private const val LANGUAGE_PARAM = "language"