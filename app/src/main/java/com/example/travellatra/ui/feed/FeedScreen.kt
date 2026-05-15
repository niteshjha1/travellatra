package com.example.travellatra.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travellatra.data.model.VideoFile
import com.example.travellatra.data.model.VideoItem
import com.example.travellatra.player.PlayerManager
import com.example.travellatra.player.VideoPlayer

@Composable
fun FeedScreen(
    viewModel: FeedViewModel = viewModel()
) {

    val context = LocalContext.current

    val playerManager = remember {
        PlayerManager(context)
    }

    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            playerManager.releasePlayer()
        }
    }

    FeedContent(
        uiState = uiState,
        playerManager = playerManager
    )
}

@Composable
fun FeedContent(
    uiState: FeedUiState,
    playerManager: PlayerManager
) {

    when {

        uiState.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error ?: "Unknown error")
            }
        }

        else -> {

            val pagerState = rememberPagerState(
                pageCount = {
                    uiState.videos.size
                }
            )

            VerticalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->

                val video = uiState.videos[page]

                val videoUrl = video.video_files.firstOrNull()?.link.orEmpty()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        // Toggle play pause on video tap
                        .clickable {
                            playerManager.togglePlayPause()
                        }
                ) {

                    if (
                        pagerState.currentPage == page &&
                        pagerState.currentPageOffsetFraction == 0f
                    ) {

                        if (!LocalInspectionMode.current) {

                            VideoPlayer(
                                modifier = Modifier.fillMaxSize(),
                                playerManager = playerManager,
                                videoUrl = videoUrl
                            )

                        } else {

                            // Preview placeholder
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.DarkGray),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "Video Preview",
                                    color = Color.White
                                )
                            }
                        }
                    }

                    // Show play icon when paused
                    if (!playerManager.isPlaying) {

                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .background(Color.Black.copy(alpha = 0.5f))
                                .padding(20.dp)
                        )
                    }

                    Text(
                        text = "Video ${video.id}",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .padding(8.dp)
                    )

                    // Mute/unmute icon
                    Icon(
                        imageVector = if (playerManager.isMuted) {
                            Icons.Default.VolumeOff
                        } else {
                            Icons.Default.VolumeUp
                        },
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .background(Color.Black.copy(alpha = 0.5f))
                            .clickable {
                                playerManager.toggleMute()
                            }
                            .padding(12.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedContentPreview() {

    val fakeVideos = listOf(
        VideoItem(
            id = 1,
            image = "",
            video_files = listOf(
                VideoFile(
                    link = ""
                )
            )
        ),
        VideoItem(
            id = 2,
            image = "",
            video_files = listOf(
                VideoFile(
                    link = ""
                )
            )
        )
    )

    FeedContent(
        uiState = FeedUiState(
            videos = fakeVideos
        ),
        playerManager = PlayerManager(LocalContext.current)
    )
}