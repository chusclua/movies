package com.chus.clua.data.network.adapter

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class CallDelegate<T, V>(
    private val call: Call<T>,
) : Call<V> {
    override fun clone(): Call<V> = cloneImpl()

    override fun execute(): Response<V> = throw UnsupportedOperationException("NetworkResponseCall")

    override fun enqueue(callback: Callback<V>) = enqueueImpl(callback)

    override fun isExecuted(): Boolean = call.isExecuted

    override fun cancel() = call.cancel()

    override fun isCanceled(): Boolean = call.isCanceled

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()

    abstract fun enqueueImpl(callback: Callback<V>)

    abstract fun cloneImpl(): Call<V>
}
