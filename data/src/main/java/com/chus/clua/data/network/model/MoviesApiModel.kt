package com.chus.clua.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviesApiModel(
    val page: Int,
    val results: List<MovieApiModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)