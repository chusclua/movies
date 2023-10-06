package com.chus.clua.presentation.person_detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chus.clua.domain.model.PersonDataDetail


@Composable
fun PeopleDetailScreenRoute(
    viewModel: PersonDetailViewModel = hiltViewModel()
) {
    val state = viewModel.detailState.collectAsStateWithLifecycle()
    PeopleDetailScreen(state.value.detail)
    
}

@Composable
private fun PeopleDetailScreen(detail: PersonDataDetail?) {
    Text(text = detail?.biography.orEmpty())
}