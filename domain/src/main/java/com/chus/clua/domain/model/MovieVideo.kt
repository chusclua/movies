package com.chus.clua.domain.model


data class MovieVideo(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
)
