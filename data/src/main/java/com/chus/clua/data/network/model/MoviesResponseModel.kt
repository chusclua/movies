package com.chus.clua.data.network.model

import com.google.gson.annotations.SerializedName

data class MoviesResponseModel(
    val page: Int,
    val results: List<MovieResponseModel>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)