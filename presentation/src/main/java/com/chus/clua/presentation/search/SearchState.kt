package com.chus.clua.presentation.search

import com.chus.clua.presentation.model.MovieList

data class SearchState(
    val movies: List<MovieList> = emptyList(),
)