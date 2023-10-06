package com.chus.clua.presentation.favorites

import com.chus.clua.presentation.model.MovieList


data class FavoritesViewState(
    val movies: List<MovieList> = emptyList()
)
