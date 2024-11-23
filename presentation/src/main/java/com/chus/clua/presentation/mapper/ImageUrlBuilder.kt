package com.chus.clua.presentation.mapper

fun buildImageUrl(
    smallProfilePath: String? = null,
    bigProfilePath: String? = null,
    posterPath: String? = null,
    backDropPath: String? = null
): String {
    smallProfilePath?.let { path -> return "$BASE_SMALL_PROFILE_URL$path" }
    bigProfilePath?.let { path -> return "$BASE_BIG_PROFILE_URL$path" }
    posterPath?.let { path -> return "$BASE_POSTER_URL$path" }
    backDropPath?.let { path -> return "$BASE_BACKDROP_URL$path" }
    return ""
}

private const val BASE_SMALL_PROFILE_URL = "https://image.tmdb.org/t/p/w185"
private const val BASE_BIG_PROFILE_URL = "https://image.tmdb.org/t/p/h632"
private const val BASE_POSTER_URL = "https://image.tmdb.org/t/p/w342"
private const val BASE_BACKDROP_URL = "https://image.tmdb.org/t/p/w780"
