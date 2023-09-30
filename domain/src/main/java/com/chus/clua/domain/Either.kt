package com.chus.clua.domain

sealed class Either<out L, out R> {
    data class Left<out L>(val error: L) : Either<L, Nothing>()
    data class Right<out R>(val data: R) : Either<Nothing, R>()
}

val <L, R> Either<L, R>.isLeft get() = this is Either.Left

val <L, R> Either<L, R>.isRight get() = this is Either.Right

val <L, R> Either<L, R>.error: L
    get() = when (this) {
        is Either.Left -> this.error
        else -> throw NoSuchElementException()
    }

val <L, R> Either<L, R>.data: R
    get() = when (this) {
        is Either.Right -> this.data
        else -> throw NoSuchElementException()
    }

fun <E, D> Either<E, D>.fold(leftOp: (E) -> Unit, rightOp: (D) -> Unit) {
    when (this) {
        is Either.Left -> leftOp(this.error)
        is Either.Right -> rightOp(this.data)
    }
}

fun <L, R> Either<L, R>.onRight(rightOp: (R) -> Unit) {
    if (this.isRight) { rightOp(this.data) }
}

fun <L, R> Either<L, R>.onLeft(leftOp: (L) -> Unit) {
    if (this.isLeft) { leftOp(this.error) }
}

fun <T, L, R> Either<L, R>.map(rightOp: (R) -> (T)): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(error)
        is Either.Right -> Either.Right(rightOp(data))
    }

fun <T, L, R> Either<L, R>.flatMap(rightOp: (R) -> Either<L, T>): Either<L, T> =
    when (this) {
        is Either.Left -> Either.Left(error)
        is Either.Right -> rightOp(data)
    }