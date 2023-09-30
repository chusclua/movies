package com.chus.clua.data.network


import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HeaderInterceptor @Inject constructor(): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        chain.request().newBuilder().apply {
            addHeader(AUTH_HEADER, "$BEARER eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJhODZhOGQzOTAyNzQ1NWJjNGI5ZGRlMzM0NWRiY2FjZCIsInN1YiI6IjY1MTY4MmE1OTNiZDY5MDBmZTQ4MTA5NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.EYwIJIh9MIsjbew6h1jXXY_ckPpPOXzg4xGyFdGZW4w")
        } .run {
            return chain.proceed(this.build())
        }
    }
}

private const val AUTH_HEADER = "Authorization"
private const val BEARER = "Bearer"