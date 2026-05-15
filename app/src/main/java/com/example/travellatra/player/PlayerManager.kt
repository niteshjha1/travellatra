package com.example.travellatra.player

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.media3.common.Player

class PlayerManager(
    private val context: Context
) {

    var isLoading by mutableStateOf(false)
        private set
    private var isMuted = true

    private var currentVideoUrl: String? = null

    private var _exoPlayer: ExoPlayer? = null
    val exoPlayer: ExoPlayer
        get() {
            if (_exoPlayer == null) {
                _exoPlayer = ExoPlayer.Builder(context).build().apply {
                    volume = 0f
                    addListener(object : Player.Listener {
                        override fun onPlaybackStateChanged(playbackState: Int) {
                            this@PlayerManager.isLoading = playbackState == Player.STATE_BUFFERING
                            Log.d("PlayerManager", "Playback state: $playbackState")
                        }
                    })
                }
            }
            return _exoPlayer!!
        }

    fun playVideo(videoUrl: String) {
        if (currentVideoUrl == videoUrl) {
            exoPlayer.playWhenReady = true
            return
        }

        currentVideoUrl = videoUrl
        Log.d("PlayerManager", "Play video: $videoUrl")
        val mediaItem = MediaItem.fromUri(videoUrl)

        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        exoPlayer.playWhenReady = true
    }

    fun pauseVideo() {
        Log.d("PlayerManager", "Pause video")
        _exoPlayer?.pause()
    }

    fun toggleMute() {
        isMuted = !isMuted
        _exoPlayer?.volume = if (isMuted) 0f else 1f
        Log.d("PlayerManager", "Muted: $isMuted")
    }

    fun isMuted(): Boolean {
        return isMuted
    }

    fun releasePlayer() {
        Log.d("PlayerManager", "Release player")
        _exoPlayer?.release()
        _exoPlayer = null
    }
}