package com.chus.clua.data.network.model

data class MovieCreditsApiModel(
    val id: Int,
    val cast: List<CastApiModel>,
    val crew: List<CrewApiModel>
)
