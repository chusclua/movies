package com.chus.clua.data.network.interceptors

import com.chus.clua.data.datasource.NetworkDataSource
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CacheInterceptor(
    private val networkDataSource: NetworkDataSource,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().let { request ->
            val nRequest =
                when {
                    !request.method.equals(GET_METHOD, ignoreCase = true) -> request
                    networkDataSource.isInternetAvailable() -> request.onlineRequest()
                    else -> request.offLineRequest()
                }
            return chain.proceed(nRequest)
        }
    }

    private fun Request.onlineRequest(): Request {
        val cacheControl =
            CacheControl
                .Builder()
                .maxAge(5, TimeUnit.MINUTES)
                .build()
        return newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .removeHeader(PRAGMA_HEADER)
            .build()
    }

    private fun Request.offLineRequest(): Request {
        val cacheControl =
            CacheControl
                .Builder()
                .maxStale(30, TimeUnit.DAYS)
                .onlyIfCached()
                .build()
        return newBuilder()
            .header(CACHE_CONTROL_HEADER, cacheControl.toString())
            .removeHeader(PRAGMA_HEADER)
            .build()
    }
}

private const val CACHE_CONTROL_HEADER = "Cache-Control"
private const val PRAGMA_HEADER = "Pragma"
private const val GET_METHOD = "GET"
