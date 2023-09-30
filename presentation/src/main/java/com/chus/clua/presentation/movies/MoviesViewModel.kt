package com.chus.clua.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.usecase.GetDiscoverMoviesUseCase
import com.chus.clua.presentation.model.MovieList
import com.chus.clua.presentation.toMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val useCase: GetDiscoverMoviesUseCase,
): ViewModel() {

    private val _moviesFlow: MutableStateFlow<PagingData<MovieList>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesFlow: StateFlow<PagingData<MovieList>> get() = _moviesFlow

    init {
        viewModelScope.launch {
            useCase()
                .distinctUntilChanged()
                .cachedIn(viewModelScope).map { pagingData ->
                    pagingData.map {
                        it.toMovieList()
                    }
                }
                .collect { data ->
                    _moviesFlow.value = data
                }
        }
    }
}