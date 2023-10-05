package com.chus.clua.presentation.mapper

import com.chus.clua.domain.model.MovieVideo
import com.chus.clua.presentation.model.VideoList


fun MovieVideo.toVideoList() =
    VideoList(
        id = id,
        name = name,
        thumbnailUrl = "$YOUTUBE_THUMBNAIL_URL$key$YOUTUBE_THUMBNAIL_EXTENSION",
        youtubeUrl = "$YOUTUBE_VIDEO_URL$key"
    )


private const val YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v="
private const val YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/"
private const val YOUTUBE_THUMBNAIL_EXTENSION = "/default.jpg"