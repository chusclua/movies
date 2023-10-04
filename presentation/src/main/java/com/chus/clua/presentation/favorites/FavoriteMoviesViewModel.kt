package com.chus.clua.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.usecase.GetFavoriteMoviesUseCase
import com.chus.clua.presentation.mapper.toMovieList
import com.chus.clua.presentation.model.MovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val favoriteMoviesUseCase: GetFavoriteMoviesUseCase
): ViewModel() {

    private val _moviesFlow: MutableStateFlow<List<MovieList>> =
        MutableStateFlow(value = emptyList())
    val moviesFlow: StateFlow<List<MovieList>> get() = _moviesFlow

    init {
        viewModelScope.launch {
            favoriteMoviesUseCase()
                .distinctUntilChanged()
                .collect { list ->
                    _moviesFlow.value = list.map { it.toMovieList() }
                }
        }
    }

}