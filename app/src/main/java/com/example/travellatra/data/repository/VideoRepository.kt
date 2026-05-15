package com.example.travellatra.data.repository

import com.example.travellatra.BuildConfig
import com.example.travellatra.data.model.VideoItem
import com.example.travellatra.data.network.RetrofitClient

class VideoRepository {

    private val apiKey = BuildConfig.PEXELS_API_KEY

    suspend fun getVideos(): List<VideoItem> {
        return RetrofitClient.apiService
            .getPopularVideos(apiKey = apiKey)
            .videos
    }
}