package com.chus.clua.domain


sealed class AppError(open val cause: String?) {
    data class InsufficientData(override val cause: String?): AppError(cause)
    data class Unknown(override val cause: String?) : AppError(cause)
    data class HttpError(val code: Int, override val cause: String?) : AppError(cause)
    data class NetworkError(override val cause: String? = null) : AppError(cause)
    data class UnexpectedError(override val cause: String? = null) : AppError(cause)
}
