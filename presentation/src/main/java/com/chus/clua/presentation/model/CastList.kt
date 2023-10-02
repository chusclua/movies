package com.chus.clua.presentation.model

data class CastList(
    override val id: Int,
    val character: String,
    override val name: String,
    override val profilePath: String?,
): People
