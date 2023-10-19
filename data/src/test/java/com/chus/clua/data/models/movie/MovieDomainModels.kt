package com.chus.clua.data.models.movie

import com.chus.clua.domain.model.Movie
import com.chus.clua.domain.model.MovieCast
import com.chus.clua.domain.model.MovieCollection
import com.chus.clua.domain.model.MovieCredits
import com.chus.clua.domain.model.MovieCrew
import com.chus.clua.domain.model.MovieDataDetail
import com.chus.clua.domain.model.MovieVideo
import com.chus.clua.domain.model.MovieVideos


val Movie = Movie(
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

val MovieDetail = MovieDataDetail(
    backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    belongsToCollection = MovieCollection(
        backdropPath = "/zqV8MGXfpLZiFVObLxpAI7wWonJ.jpg",
        id = 230,
        name = "The Godfather Collection",
        posterPath = "/3WZTxpgscsmoUk81TuECXdFOD0R.jpg"
    ),
    genres = listOf("Drama", "Crime"),
    id = 238,
    originalTitle = "The Godfather",
    overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
    popularity = 117.613,
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    productionCompanies = listOf("Paramount", "Alfran Productions"),
    productionCountries = listOf("United States of America"),
    releaseDate = "1972-03-14",
    tagline = "An offer you can't refuse.",
    title = "The Godfather",
    voteAverage = 8.709,
    voteCount = 18755
)

val MovieCast = MovieCast(
    id = 3084,
    character = "Don Vito Corleone",
    name = "Marlon Brando",
    profilePath = "/eEHCjqKMWSvQU4bmwhLMsg4RtEr.jpg",
    popularity = 17.648
)

val MovieCrew = MovieCrew(
    id = 1776,
    job = "Director",
    name = "Francis Ford Coppola",
    profilePath = "/3Pblihd6KjXliie9vj4iQJwbNPU.jpg",
    popularity = 12.19
)

val MovieCredits = MovieCredits(
    id = 1,
    cast = listOf(MovieCast),
    crew = listOf(MovieCrew)
)

val MovieVideo = MovieVideo(
    id = "627e773920e6a534876391df",
    key = "wA6iB3OZDus",
    name = "The Godfather Never Before Seen Footage (Rare Footage 1971)",
    official = false,
    publishedAt = "2022-04-20T13:00:24.000Z",
    site = "YouTube",
    size = 1080,
    type = "Featurette"
)

val MovieVideos = MovieVideos(
    id = 238,
    videos = listOf(MovieVideo)
)