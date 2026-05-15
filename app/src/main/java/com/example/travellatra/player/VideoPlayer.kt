package com.example.travellatra.player

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    playerManager: PlayerManager,
    videoUrl: String,
    isVisible: Boolean
) {

    LaunchedEffect(isVisible) {

        if (isVisible) {
            playerManager.playVideo(videoUrl)
        } else {
            playerManager.pauseVideo()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->

            PlayerView(context).apply {

                player = playerManager.exoPlayer
                useController = false

                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        }
    )

    DisposableEffect(Unit) {
        onDispose {
            playerManager.pauseVideo()
        }
    }
}