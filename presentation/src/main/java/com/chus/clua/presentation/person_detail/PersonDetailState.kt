package com.chus.clua.presentation.person_detail

import com.chus.clua.presentation.model.PersonDetailUi


data class PersonDetailState(
    val detail: PersonDetailUi? = null,
    val error: Boolean = false
)
