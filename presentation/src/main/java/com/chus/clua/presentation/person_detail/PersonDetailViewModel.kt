package com.chus.clua.presentation.person_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.fold
import com.chus.clua.domain.model.PersonDataDetail
import com.chus.clua.domain.model.PersonMovieCast
import com.chus.clua.domain.model.PersonMovieCrew
import com.chus.clua.domain.usecase.GetPersonMovieDetailUseCase
import com.chus.clua.presentation.mapper.toPersonDetailUi
import com.chus.clua.presentation.mapper.toPersonMovieCastList
import com.chus.clua.presentation.mapper.toPersonMovieCrewList
import com.chus.clua.presentation.navigation.NavigationScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val personMovieDetailUseCase: GetPersonMovieDetailUseCase
) : ViewModel() {

    private val _detailState: MutableStateFlow<PersonDetailState> by lazy {
        MutableStateFlow(PersonDetailState())
    }
    val detailState: StateFlow<PersonDetailState> get() = _detailState

    init {
        val personId =
            savedStateHandle.get<Int>(NavigationScreens.PeopleDetail.paramId) ?: Int.MIN_VALUE
        viewModelScope.launch {
            personMovieDetailUseCase(personId).fold(
                leftOp = {
                    _detailState.update {
                        it.copy(error = true)
                    }
                },
                rightOp = { detail ->
                    val (personDataDetail, cast, crew) = detail
                    updateState(personDataDetail, cast, crew)
                }
            )
        }
    }

    private fun updateState(
        personDataDetail: PersonDataDetail,
        cast: List<PersonMovieCast>,
        crew: List<PersonMovieCrew>
    ) {
        _detailState.update {
            it.copy(
                detail = personDataDetail.toPersonDetailUi(),
                cast = cast.map { it.toPersonMovieCastList() },
                crew = crew.map { it.toPersonMovieCrewList() }
            )
        }
    }
}