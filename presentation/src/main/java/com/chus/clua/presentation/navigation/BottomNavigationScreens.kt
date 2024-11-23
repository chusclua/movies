package com.chus.clua.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.chus.clua.presentation.R

sealed class BottomNavigationScreens(
    @StringRes
    val title: Int,
    val route: String,
    val vector: ImageVector
) {
    object Movies : BottomNavigationScreens(
        title = R.string.movies,
        route = "movies",
        vector = Icons.Filled.List
    )

    object Favorites : BottomNavigationScreens(
        title = R.string.favorites,
        route = "favorites",
        vector = Icons.Filled.FavoriteBorder
    )

    object Search : BottomNavigationScreens(
        title = R.string.search,
        route = "search",
        vector = Icons.Filled.Search
    )
}
