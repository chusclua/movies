package com.chus.clua.presentation.person_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chus.clua.domain.AppError
import com.chus.clua.domain.fold
import com.chus.clua.domain.model.PersonDetail
import com.chus.clua.domain.usecase.GetPersonDetailUseCase
import com.chus.clua.presentation.mapper.toPersonDetailUi
import com.chus.clua.presentation.mapper.toPersonMovieCastList
import com.chus.clua.presentation.mapper.toPersonMovieCrewList
import com.chus.clua.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


@HiltViewModel
class PersonDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val personDetailUseCase: GetPersonDetailUseCase
) : ViewModel() {

    private val _detailState: MutableStateFlow<PersonDetailState> by lazy {
        MutableStateFlow(PersonDetailState())
    }
    val detailState: StateFlow<PersonDetailState> get() = _detailState

    init {
        val personId =
            savedStateHandle.get<Int>(Screens.PersonDetail.paramId) ?: Int.MIN_VALUE
        viewModelScope.launch {
            personDetailUseCase(personId).fold(
                leftOp = ::onLeft,
                rightOp = ::onRight
            )
        }
    }

    private fun onLeft(appError: AppError) {
        _detailState.update { it.copy(error = true) }
    }

    private fun onRight(personDetail: PersonDetail) {
        val (personDataDetail, cast, crew) = personDetail
        _detailState.update {
            it.copy(
                detail = personDataDetail.toPersonDetailUi(),
                cast = cast.map { it.toPersonMovieCastList() },
                crew = crew.map { it.toPersonMovieCrewList() },
            )
        }
    }
}