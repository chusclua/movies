package com.chus.clua.presentation.favorites

import com.chus.clua.presentation.model.FavoriteMovieList


data class FavoritesViewState(
    val movies: List<FavoriteMovieList> = emptyList()
)
