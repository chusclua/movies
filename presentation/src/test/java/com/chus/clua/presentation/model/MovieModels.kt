package com.chus.clua.presentation.model

val Movies: List<MovieList> get() = (1..20).map { MovieList.copy(id = it) }

val MovieList = MovieList(
    id = 238,
    title = "The Godfather",
    backdropUrl = "https://image.tmdb.org/t/p/w780/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    posterUrl = "https://image.tmdb.org/t/p/w342/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    year = "1972",
    voteAverage = 8.709,
)
