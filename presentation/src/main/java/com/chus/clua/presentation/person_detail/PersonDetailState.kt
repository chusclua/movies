package com.chus.clua.presentation.person_detail

import com.chus.clua.domain.model.PersonDataDetail


data class PersonDetailState(
    val detail: PersonDataDetail? = null,
    val error: Boolean = false
)
