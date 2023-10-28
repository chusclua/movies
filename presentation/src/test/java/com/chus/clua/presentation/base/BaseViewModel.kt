package com.chus.clua.presentation.base

import dagger.hilt.android.testing.HiltAndroidRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseViewModel {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val testDispatcher = UnconfinedTestDispatcher()

    protected fun setUp() {
        setMain()
        inject()
    }

    protected fun tearDown() {
        resetMain()
    }

    private fun inject() {
        hiltRule.inject()
    }

    private fun setMain() {
        Dispatchers.setMain(testDispatcher)
    }

    private fun resetMain() {
        Dispatchers.resetMain()
    }
}