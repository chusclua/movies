package com.chus.clua.data.network.adapter

import com.chus.clua.domain.Either
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit

@Singleton
class EitherCallAdapterFactory @Inject constructor() : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    Either::class.java -> {
                        val resultType = getParameterUpperBound(1, callType as ParameterizedType)
                        EitherCallAdapter(resultType)
                    }
                    else -> null
                }
            }
            else -> null
        }
    }
}