package com.chus.clua.data.network.adapter

import com.chus.clua.domain.AppError
import com.chus.clua.domain.Either
import java.lang.reflect.Type
import okhttp3.Request
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<AppError, R>> {

    @Suppress("UNCHECKED_CAST")
    override fun enqueue(callback: Callback<Either<AppError, R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither()))
            }

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
                    successType == Unit::class.java -> Either.Right(Unit) as Either<AppError, R>
                    else -> return Either.Right(body()!!)
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {
                val error = when (throwable) {
                    is IOException -> AppError.NetworkError(throwable.message)
                    else -> AppError.UnexpectedError(throwable.message)
                }
                callback.onResponse(
                    this@EitherCall,
                    Response.success(Either.Left(error))
                )
            }
        }
    )

    override fun clone(): Call<Either<AppError, R>> = EitherCall(delegate.clone(), successType)

    override fun execute(): Response<Either<AppError, R>> =
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}