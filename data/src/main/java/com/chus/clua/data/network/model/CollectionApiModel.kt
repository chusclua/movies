package com.chus.clua.data.network.model

import com.google.gson.annotations.SerializedName

data class CollectionApiModel(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?,
)
