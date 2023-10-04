package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.MovieVideoResponse
import com.chus.clua.data.network.model.MovieVideosResponse
import com.chus.clua.domain.model.MovieVideo
import com.chus.clua.domain.model.MovieVideos


fun MovieVideosResponse.toMovieVideos() =
    MovieVideos(
        id = id,
        videos = results.map { it.toMovieVideo() }
    )

private fun MovieVideoResponse.toMovieVideo() =
    MovieVideo(
        id = id,
        key = key,
        name = name,
        official = official,
        publishedAt = publishedAt,
        site = site,
        size = size,
        type = type
    )