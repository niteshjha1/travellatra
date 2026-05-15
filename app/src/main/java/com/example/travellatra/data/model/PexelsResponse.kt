package com.example.travellatra.data.model

data class PexelsResponse(
    val videos: List<VideoItem>
)

data class VideoItem(
    val id: Int,
    val image: String,
    val video_files: List<VideoFile>
)

data class VideoFile(
    val link: String
)