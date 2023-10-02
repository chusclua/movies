package com.chus.clua.presentation.model

data class CrewList(
    override val id: Int,
    val job: String,
    override val name: String,
    override val profilePath: String?,
): People
