package com.chus.clua.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.fold
import com.chus.clua.domain.usecase.SearchMoviesUseCase
import com.chus.clua.presentation.mapper.toMovieList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private var recommendationJob: Job? = null

    private val _searchState: MutableStateFlow<SearchState> by lazy { MutableStateFlow(SearchState()) }
    val searchState: StateFlow<SearchState> get() = _searchState


    fun search(query: String) {
        if (query.isEmpty()) {
            recommendationJob?.cancel()
            return
        }
        recommendationJob?.cancel()
        recommendationJob = viewModelScope.launch {
            delay(QUERY_DEBOUNCE)
            searchMoviesUseCase(query).fold(
                leftOp = {},
                rightOp = { movies ->
                    _searchState.update {
                        it.copy(
                            movies = movies.map { movie -> movie.toMovieList() },
                            search = true
                        )
                    }
                }
            )
        }
    }

    companion object {
        private const val QUERY_DEBOUNCE = 1000L
    }

}