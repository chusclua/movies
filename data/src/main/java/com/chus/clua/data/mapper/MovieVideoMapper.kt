package com.chus.clua.data.mapper

import com.chus.clua.data.network.model.MovieVideoApiModel
import com.chus.clua.data.network.model.MovieVideosApiModel
import com.chus.clua.domain.model.MovieVideo
import com.chus.clua.domain.model.MovieVideos

fun MovieVideosApiModel.toMovieVideos() =
    MovieVideos(
        id = id,
        videos = results.map { it.toMovieVideo() },
    )

private fun MovieVideoApiModel.toMovieVideo() =
    MovieVideo(
        id = id,
        key = key,
        name = name,
        official = official,
        publishedAt = publishedAt,
        site = site,
        size = size,
        type = type,
    )
