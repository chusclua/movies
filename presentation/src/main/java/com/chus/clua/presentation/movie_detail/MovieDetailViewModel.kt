package com.chus.clua.presentation.movie_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieDataDetail
import com.chus.clua.domain.model.MovieVideos
import com.chus.clua.domain.usecase.GetMovieDetailUseCase
import com.chus.clua.domain.usecase.ToggleFavoriteMovieUseCase
import com.chus.clua.presentation.mapper.toCastList
import com.chus.clua.presentation.mapper.toCrewList
import com.chus.clua.presentation.mapper.toMovieDetail
import com.chus.clua.presentation.mapper.toVideoList
import com.chus.clua.presentation.navigation.NavigationScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val toggleFavoriteMovieUseCase: ToggleFavoriteMovieUseCase
) : ViewModel() {

    private val _detailState: MutableStateFlow<MovieDetailViewState> by lazy {
        MutableStateFlow(MovieDetailViewState())
    }
    val detailState: StateFlow<MovieDetailViewState> get() = _detailState

    init {
        val movieId =
            savedStateHandle.get<Int>(NavigationScreens.MovieDetails.paramId) ?: Int.MIN_VALUE
        viewModelScope.launch {
            val (isFavorite, dataDetail, credits, videos) = movieDetailUseCase(movieId)
            updateState(isFavorite, dataDetail, credits, videos)
        }
    }

    fun toggleOnFavorites() {
        _detailState.value.movieDetail?.let { movie ->
            viewModelScope.launch {
                toggleFavoriteMovieUseCase(movie.id, _detailState.value.isFavorite)
                _detailState.update {
                    it.copy(isFavorite = !_detailState.value.isFavorite)
                }
            }
        }
    }

    private fun updateState(
        isFavorite: Boolean,
        movieDataDetail: MovieDataDetail?,
        credits: MovieCredits?,
        videos: MovieVideos?
    ) {
        movieDataDetail?.let { data ->
            _detailState.update {
                it.copy(
                    isFavorite = isFavorite,
                    movieDetail = data.toMovieDetail(),
                    cast = credits?.toCastList() ?: emptyList(),
                    crew = credits?.toCrewList() ?: emptyList(),
                    videos = videos?.videos?.map { video -> video.toVideoList() } ?: emptyList()
                )
            }
        } ?: {
            _detailState.update { it.copy(error = true) }
        }
    }

}