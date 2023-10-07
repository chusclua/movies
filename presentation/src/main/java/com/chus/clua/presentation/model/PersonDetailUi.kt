package com.chus.clua.presentation.model


data class PersonDetailUi(
    val biography: String,
    val homepage: String?,
    val id: Int,
    val name: String,
    val popularity: Double,
    val profilePath: String
)
