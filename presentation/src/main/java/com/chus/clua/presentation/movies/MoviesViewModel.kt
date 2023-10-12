package com.chus.clua.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.chus.clua.domain.usecase.GetDiscoverMoviesUseCase
import com.chus.clua.domain.usecase.GetTopRatedMoviesUseCase
import com.chus.clua.presentation.model.MovieList
import com.chus.clua.presentation.mapper.toMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val discoverMoviesUseCase: GetDiscoverMoviesUseCase,
    private val topRatedMoviesUseCase: GetTopRatedMoviesUseCase
): ViewModel() {

    private val _moviesFlow: MutableStateFlow<PagingData<MovieList>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesFlow: StateFlow<PagingData<MovieList>> get() = _moviesFlow

    private val _topRatedMoviesFlow: MutableStateFlow<PagingData<MovieList>> =
        MutableStateFlow(value = PagingData.empty())
    val topRatedMoviesFlow: StateFlow<PagingData<MovieList>> get() = _topRatedMoviesFlow

    init {
        viewModelScope.launch {
            getDiscoverMovies()
        }
        viewModelScope.launch {
            getPopularMovies()
        }
    }

    private suspend fun getDiscoverMovies() {
        discoverMoviesUseCase()
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

    private suspend fun getPopularMovies() {
        topRatedMoviesUseCase()
            .distinctUntilChanged()
            .cachedIn(viewModelScope).map { pagingData ->
                pagingData.map {
                    it.toMovieList()
                }
            }
            .collect { data ->
                _topRatedMoviesFlow.value = data
            }
    }
}