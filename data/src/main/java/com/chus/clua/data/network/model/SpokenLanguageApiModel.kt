package com.chus.clua.data.network.model

import com.google.gson.annotations.SerializedName

data class SpokenLanguageApiModel(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("iso_639_1")
    val iso: String,
    val name: String,
)
