package com.chus.clua.data.models

import com.chus.clua.data.db.entity.MovieEntity

val MovieEntities: List<MovieEntity> get() = (1..20).map { MovieEntity }

val MovieEntity = MovieEntity(
    adult = false,
    backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    genreIds = listOf(18, 80),
    id = 238,
    originalLanguage = "en",
    originalTitle = "The Godfather",
    overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
    popularity = 117.613,
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    releaseDate = "1972-03-14",
    title = "The Godfather",
    video = false,
    voteAverage = 8.709,
    voteCount = 18755
)