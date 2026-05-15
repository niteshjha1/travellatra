package com.example.travellatra.ui.feed

import com.example.travellatra.data.model.VideoItem

data class FeedUiState(
    val isLoading: Boolean = false,
    val videos: List<VideoItem> = emptyList(),
    val error: String? = null
)