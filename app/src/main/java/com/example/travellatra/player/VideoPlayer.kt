package com.example.travellatra.player

import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.ui.PlayerView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    playerManager: PlayerManager,
    videoUrl: String
) {

    val isLoading = playerManager.isLoading

    LaunchedEffect(videoUrl) {
        playerManager.playVideo(videoUrl)
    }

    Box(
        modifier = modifier
    ) {

        AndroidView(
            modifier = Modifier.fillMaxSize(),
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

        if (isLoading) {

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}