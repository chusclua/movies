package com.chus.clua.data.network.api.fake

abstract class BaseFakeApi {
    protected var isLeft: Boolean = false

    fun forceLeft() {
        isLeft = true
    }
}
