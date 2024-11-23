package com.chus.clua.data.models.movie

import com.chus.clua.data.network.model.CastApiModel
import com.chus.clua.data.network.model.CollectionApiModel
import com.chus.clua.data.network.model.CrewApiModel
import com.chus.clua.data.network.model.GenreApiModel
import com.chus.clua.data.network.model.MovieApiModel
import com.chus.clua.data.network.model.MovieCreditsApiModel
import com.chus.clua.data.network.model.MovieDetailApiModel
import com.chus.clua.data.network.model.MovieVideoApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.data.network.model.MoviesApiModel
import com.chus.clua.data.network.model.ProductionCompanyApiModel
import com.chus.clua.data.network.model.ProductionCountryApiModel
import com.chus.clua.data.network.model.SpokenLanguageApiModel

val MovieDetailApi = MovieDetailApiModel(
    adult = false,
    backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    belongsToCollection = CollectionApiModel(
        backdropPath = "/zqV8MGXfpLZiFVObLxpAI7wWonJ.jpg",
        id = 230,
        name = "The Godfather Collection",
        posterPath = "/3WZTxpgscsmoUk81TuECXdFOD0R.jpg"
    ),
    budget = 6000000,
    genres = listOf(GenreApiModel(18, "Drama"), GenreApiModel(80, "Crime")),
    homepage = "http://www.thegodfather.com/",
    id = 238,
    imdbId = "tt0068646",
    originalLanguage = "en",
    originalTitle = "The Godfather",
    overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American " +
        "Corleone crime family. When organized crime family patriarch, Vito Corleone barely " +
        "survives an attempt on his life, his youngest son, Michael steps in to take care of the " +
        "would-be killers, launching a campaign of bloody revenge.",
    popularity = 117.613,
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    productionCompanies = listOf(
        ProductionCompanyApiModel(
            id = 4,
            logoPath = "/gz66EfNoYPqHTYI4q9UEN4CbHRc.png",
            name = "Paramount",
            originCountry = "US"
        ),
        ProductionCompanyApiModel(
            id = 10211,
            logoPath = null,
            name = "Alfran Productions",
            originCountry = "US"
        )
    ),
    productionCountries = listOf(
        ProductionCountryApiModel(
            iso = "US",
            name = "United States of America"
        )
    ),
    releaseDate = "1972-03-14",
    revenue = 245066411,
    runtime = 175,
    spokenLanguages = listOf(
        SpokenLanguageApiModel(
            englishName = "English",
            iso = "en",
            name = "English"
        ),
        SpokenLanguageApiModel(
            englishName = "Italian",
            iso = "it",
            name = "Italiano"
        ),
        SpokenLanguageApiModel(
            englishName = "Latin",
            iso = "la",
            name = "Latin"
        )
    ),
    status = "Released",
    tagline = "An offer you can't refuse.",
    title = "The Godfather",
    video = false,
    voteAverage = 8.709,
    voteCount = 18755
)

val CastApiModel = CastApiModel(
    adult = false,
    castId = 146,
    character = "Don Vito Corleone",
    creditId = "6489aa85e2726001072483a9",
    gender = 2,
    id = 3084,
    knownForDepartment = "Acting",
    name = "Marlon Brando",
    order = 0,
    originalName = "Marlon Brando",
    popularity = 17.648,
    profilePath = "/eEHCjqKMWSvQU4bmwhLMsg4RtEr.jpg"
)

val CrewApiModel = CrewApiModel(
    adult = false,
    creditId = "52fe422bc3a36847f80093db",
    department = "Directing",
    gender = 2,
    id = 1776,
    job = "Director",
    knownForDepartment = "Directing",
    name = "Francis Ford Coppola",
    originalName = "Francis Ford Coppola",
    popularity = 12.19,
    profilePath = "/3Pblihd6KjXliie9vj4iQJwbNPU.jpg"
)

val MovieCreditsApi = MovieCreditsApiModel(
    id = 1,
    cast = listOf(CastApiModel),
    crew = listOf(CrewApiModel)
)

val MovieApiList: List<MovieApiModel> get() = (1..20).map { MovieApi.copy(id = it) }

val MovieApi = MovieApiModel(
    adult = false,
    backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",
    genreIds = listOf(18, 80),
    id = 238,
    originalLanguage = "en",
    originalTitle = "The Godfather",
    overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American " +
        "Corleone crime family. When organized crime family patriarch, Vito Corleone barely " +
        "survives an attempt on his life, his youngest son, Michael steps in to take care of the " +
        "would-be killers, launching a campaign of bloody revenge.",
    popularity = 117.613,
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    releaseDate = "1972-03-14",
    title = "The Godfather",
    video = false,
    voteAverage = 8.709,
    voteCount = 18755
)

val MoviesApi = MoviesApiModel(
    page = 1,
    results = MovieApiList,
    totalPages = 40514,
    totalResults = 810266
)

val MovieVideoApi = MovieVideoApiModel(
    id = "627e773920e6a534876391df",
    iso6391 = "US",
    iso31661 = "en",
    key = "wA6iB3OZDus",
    name = "The Godfather Never Before Seen Footage (Rare Footage 1971)",
    official = false,
    publishedAt = "2022-04-20T13:00:24.000Z",
    site = "YouTube",
    size = 1080,
    type = "Featurette"
)

val MovieVideosApi = MovieVideosApiModel(
    id = 238,
    results = listOf(MovieVideoApi)
)
