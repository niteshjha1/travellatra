package com.example.travellatra.player

import android.content.Context
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer

class PlayerManager(
    context: Context
) {

    val exoPlayer: ExoPlayer = ExoPlayer.Builder(context).build()

    fun playVideo(videoUrl: String) {

        Log.d("PlayerManager", "Play video: $videoUrl")

        val mediaItem = MediaItem.fromUri(videoUrl)

        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    fun pauseVideo() {

        Log.d("PlayerManager", "Pause video")

        exoPlayer.pause()
    }

    fun releasePlayer() {

        Log.d("PlayerManager", "Release player")

        exoPlayer.release()
    }
}