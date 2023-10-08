package com.chus.clua.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.usecase.GetFavoriteMoviesUseCase
import com.chus.clua.presentation.mapper.toFavoriteMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteMoviesUseCase: GetFavoriteMoviesUseCase
) : ViewModel() {

    private val _moviesFlow: MutableStateFlow<FavoritesViewState> =
        MutableStateFlow(value = FavoritesViewState())
    val moviesFlow: StateFlow<FavoritesViewState> get() = _moviesFlow

    init {
        viewModelScope.launch {
            favoriteMoviesUseCase()
                .distinctUntilChanged()
                .collect { list ->
                    _moviesFlow.update {
                        it.copy(movies = list.map { movie -> movie.toFavoriteMovieList() })
                    }
                }
        }
    }

    fun onClearAllClicked() {
        // TODO:
        _moviesFlow.update {
            it.copy(movies = emptyList())
        }
    }

}