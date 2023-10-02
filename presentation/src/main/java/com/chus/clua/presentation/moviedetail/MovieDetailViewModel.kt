package com.chus.clua.presentation.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.Either
import com.chus.clua.domain.fold
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieData
import com.chus.clua.domain.onRight
import com.chus.clua.domain.usecase.GetMovieCreditsUseCase
import com.chus.clua.domain.usecase.GetMovieDetailUseCase
import com.chus.clua.presentation.mapper.toCastList
import com.chus.clua.presentation.mapper.toCrewList
import com.chus.clua.presentation.mapper.toMovieDetail
import com.chus.clua.presentation.navigation.NavigationScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val movieCreditsUseCase: GetMovieCreditsUseCase
) : ViewModel() {

    private val _detailState: MutableStateFlow<MovieDetailViewState> by lazy {
        MutableStateFlow(MovieDetailViewState())
    }
    val detailState: StateFlow<MovieDetailViewState> get() = _detailState

    init {
        val movieId = savedStateHandle.get<Int>(NavigationScreens.MOVIE_DETAIL.param) ?: Int.MIN_VALUE
        viewModelScope.launch {
            val credits = async { getCredits(movieId) }
            val detail = async { getDetail(movieId) }
            updateAll(credits.await(), detail.await())
        }
    }

    fun toggleOnFavorites() {
        _detailState.value.movieDetail?.id?.let { movieId ->
            // TODO: toggle on favorites 
        }
    }

    private suspend fun getDetail(movieId: Int) =
        movieDetailUseCase(movieId)

    private suspend fun getCredits(movieId: Int) =
        movieCreditsUseCase(movieId)

    private fun updateAll(
        credits: Either<Exception, MovieCredits>,
        detail: Either<Exception, MovieData>
    ) {
        credits.onRight { data ->
            _detailState.update {
                it.copy(
                    cast = data.toCastList(),
                    crew = data.toCrewList()
                )
            }
        }
        detail.fold(
            leftOp = {
                _detailState.update { it.copy(error = true) }
            },
            rightOp = { data ->
                _detailState.update { it.copy(movieDetail = data.toMovieDetail()) }
            }
        )
    }

}