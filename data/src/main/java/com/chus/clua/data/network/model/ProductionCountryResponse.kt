package com.chus.clua.data.network.model

import com.google.gson.annotations.SerializedName

data class ProductionCountryResponse(
    @SerializedName("iso_3166_1")
    val iso: String,
    val name: String
)