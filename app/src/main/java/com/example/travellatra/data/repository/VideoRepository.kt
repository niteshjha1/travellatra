package com.example.travellatra.data.repository

import com.example.travellatra.data.model.VideoItem
import com.example.travellatra.data.network.RetrofitClient

class VideoRepository {

    private val apiKey = "NoTokenPush"

    suspend fun getVideos(): List<VideoItem> {
        return RetrofitClient.apiService
            .getPopularVideos(apiKey = apiKey)
            .videos
    }
}