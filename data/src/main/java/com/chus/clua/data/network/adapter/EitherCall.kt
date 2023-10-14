package com.chus.clua.data.network.adapter

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import java.lang.reflect.Type
import okio.IOException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EitherCall<R>(
    private val call: Call<R>,
    private val successType: Type
) : CallDelegate<R, Either<AppError, R>>(call) {

    override fun enqueueImpl(callback: Callback<Either<AppError, R>>) =
        call.enqueue(object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither()))
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                callback.onResponse(
                    this@EitherCall,
                    Response.success(throwable.toEither())
                )
            }
        })

    override fun cloneImpl(): Call<Either<AppError, R>> = EitherCall(call.clone(), successType)

    @Suppress("UNCHECKED_CAST")
    private fun Response<R>.toEither(): Either<AppError, R> {
        return when {
            !isSuccessful -> {
                val error = AppError.HttpError(code(), message())
                Either.Left(error)
            }

            body() == null -> {
                val error = AppError.Unknown("Response body is null")
                Either.Left(error)
            }

            successType == Unit::class.java -> {
                Either.Right(Unit) as Either<AppError, R>
            }

            else -> Either.Right(body()!!)
        }
    }

    private fun Throwable.toEither(): Either<AppError, R> {
        return when (this) {
            is IOException -> Either.Left(AppError.NetworkError(this.message))
            else -> Either.Left(AppError.UnexpectedError(this.message))
        }
    }
}