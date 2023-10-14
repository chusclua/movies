package com.chus.clua.data.network.adapter

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import java.lang.reflect.Type
import retrofit2.Call
import retrofit2.CallAdapter

class EitherCallAdapter(
    private val successType: Type
) : CallAdapter<Type, Call<Either<AppError, Type>>> {

    override fun adapt(call: Call<Type>): Call<Either<AppError, Type>> = EitherCall(call, successType)

    override fun responseType(): Type = successType
}