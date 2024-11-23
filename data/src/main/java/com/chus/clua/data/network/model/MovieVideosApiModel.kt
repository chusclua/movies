package com.chus.clua.data.network.model

data class MovieVideosApiModel(
    val id: Int,
    val results: List<MovieVideoApiModel>,
)
