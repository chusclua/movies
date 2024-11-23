package com.chus.clua.presentation.compose.extensions

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyGridState(): LazyGridState {
    return when (itemCount) {
        0 -> remember(this) { LazyGridState(0, 0) }
        else -> androidx.compose.foundation.lazy.grid.rememberLazyGridState()
    }
}
