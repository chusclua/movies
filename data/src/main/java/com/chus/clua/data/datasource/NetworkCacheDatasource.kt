package com.chus.clua.data.datasource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import javax.inject.Inject
import javax.inject.Singleton

interface NetworkCacheDatasource {
    fun getCache(): Cache
}

@Singleton
class NetworkCacheDatasourceImp
    @Inject
    constructor(
        @ApplicationContext private val appContext: Context,
    ) : NetworkCacheDatasource {
        private val cacheSize = (10 * 1024 * 1024).toLong()

        override fun getCache() = Cache(appContext.cacheDir, cacheSize)
    }
