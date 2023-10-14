package com.chus.clua.data.network.adapter

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<AppError, R>>> {

    override fun adapt(call: Call<R>): Call<Either<AppError, R>> = EitherCall(call, successType)

    override fun responseType(): Type = successType
}