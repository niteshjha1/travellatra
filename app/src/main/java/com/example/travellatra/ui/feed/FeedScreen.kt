package com.example.travellatra.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    modifier = Modifier.fillMaxSize()
                ) {

                    VideoPlayer(
                        modifier = Modifier.fillMaxSize(),
                        playerManager = playerManager,
                        videoUrl = videoUrl,
                        isVisible = pagerState.currentPage == page
                    )

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
                }
            }
        }
    }
}