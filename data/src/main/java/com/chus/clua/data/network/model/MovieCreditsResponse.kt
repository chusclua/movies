package com.chus.clua.data.network.model

data class MovieCreditsResponse(
    val id: Int,
    val cast: List<CastResponse>,
    val crew: List<CrewResponse>
)
