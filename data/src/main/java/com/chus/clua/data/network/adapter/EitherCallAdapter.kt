package com.chus.clua.data.network.adapter

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class EitherCallAdapter(
    private val type: Type,
) : CallAdapter<Type, Call<Either<AppError, Type>>> {
    override fun adapt(call: Call<Type>): Call<Either<AppError, Type>> = EitherCall(call, type)

    override fun responseType(): Type = type
}
