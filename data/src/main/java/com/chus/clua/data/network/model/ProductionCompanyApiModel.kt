package com.chus.clua.data.network.model

import com.google.gson.annotations.SerializedName

data class ProductionCompanyApiModel(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String,
)
