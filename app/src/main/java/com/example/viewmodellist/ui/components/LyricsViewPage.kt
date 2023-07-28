package com.example.viewmodellist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dokar.amlv.FadingEdges
import com.dokar.amlv.Lyrics
import com.dokar.amlv.LyricsViewState
import com.dokar.amlv.rememberLyricsViewState
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.amlv.src.main.java.com.dokar.amlv.LyricsView
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlin.math.sqrt

@Composable
fun LyricsViewPage(
    picUrl: String,
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    state: LyricsViewState,
    modifier: Modifier = Modifier
) {

    LaunchedEffect(mediaPlayerViewModel.isPlaying) {
        if (mediaPlayerViewModel.isPlaying)
            state.play()
        else
            state.pause()
    }

    var showLyc by rememberSaveable {
        mutableStateOf(true)
    }
    Column(
        modifier = modifier
            .height(800.dp)
            .animatedGradient(animating = state.mediaPlayerViewModel.isPlaying)
            .windowInsetsPadding(WindowInsets.systemBars),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TitleBar(
            lyrics = state.lyrics,
            contentColor = Color.White,
        )
        AnimatedVisibility(
            visible = showLyc,
        ) {
            Card(
                shape = MaterialTheme.shapes.small,
                colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                modifier = Modifier
                    .size(500.dp)
                    .padding(15.dp)
                    .clickable { showLyc = !showLyc },
            ) {
                Image(
                    painter = rememberAsyncImagePainter(picUrl),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = null
                )

            }
        }

        AnimatedVisibility(visible = !showLyc) {
            LyricsView(
                state = state,
                modifier = Modifier
                    .height(500.dp),
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 150.dp,
                ),
                darkTheme = true,
                fadingEdges = FadingEdges(top = 16.dp, bottom = 150.dp),
            )
        }
        PlaybackControls(
            state = state,
            modifier = Modifier,
            contentColor = Color.White,
            showLyc = showLyc,
            findviewModel,
            mediaPlayerViewModel,
            onClick = { showLyc = !showLyc }
        )
    }
}

@Composable
fun TitleBar(
    lyrics: Lyrics?,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
) {
    Column(
        modifier = modifier.padding(
            horizontal = 32.dp,
            vertical = 16.dp,
        ),
    ) {
        Text(
            text = lyrics?.title ?: "",
            color = contentColor,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        val artist = lyrics?.artist
        if (!artist.isNullOrEmpty()) {
            Text(
                text = artist,
                modifier = Modifier.alpha(0.7f),
                color = contentColor,
                fontSize = 18.sp,
            )
        }
    }
}

@Composable
fun PlaybackControls(
    state: LyricsViewState,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    showLyc: Boolean,
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    onClick: () -> Unit
) {


    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        val duration = state.lyrics?.optimalDurationMillis
        if (duration != null && duration > 0L) {
            var progress by remember { mutableStateOf(0f) }

            var positionText by remember { mutableStateOf("0:00") }

            fun millisToText(millis: Long): String {
                val minutes = millis / 1000 / 60
                val seconds = (millis - minutes * 1000 * 60) / 1000
                return "$minutes:${String.format("%02d", seconds)}"
            }

            LaunchedEffect(state, duration) {
                launch(Dispatchers.Default) {
                    snapshotFlow { state.mediaPlayerViewModel.currentPosition + 1000 }
                        .distinctUntilChanged()
                        .collect {
                            progress = ((it.toFloat() / duration) * 100).toInt() / 100f
                            positionText = millisToText(it.toLong())
                        }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    text = mediaPlayerViewModel.currentPositionText,
                    color = contentColor,
                    fontSize = 14.sp,
                )

                Text(
                    text = mediaPlayerViewModel.durationText,
                    color = contentColor,
                    fontSize = 14.sp,
                )
            }

            Slider(
                value = mediaPlayerViewModel.currentPosition.toFloat(),
                onValueChange = { newPosition ->
                    run {
                        state.seekTo(newPosition.toLong())
                        mediaPlayerViewModel.seekTo(newPosition.toInt())
                    }
                },
                valueRange = 0f..mediaPlayerViewModel.duration.toFloat(),
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White.copy(alpha = 0.8f),
                    inactiveTrackColor = Color.White.copy(alpha = 0.5f),
                ),
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            val playIcon = if (state.mediaPlayerViewModel.isPlaying) {
                R.drawable.outline_pause_24
            } else {
                R.drawable.outline_play_arrow_24
            }
            Icon(
                painter = painterResource(playIcon),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = {
                            if (state.mediaPlayerViewModel.isPlaying) {

                                mediaPlayerViewModel.pause()
                            } else {

                                mediaPlayerViewModel.resume()
                            }
                        },
                    ),
                tint = contentColor,
            )


            val showlycbtn = if (showLyc) {
                R.drawable.baseline_hide_image_24
            } else {
                R.drawable.baseline_image_24
            }
            Icon(
                painter = painterResource(showlycbtn),
                contentDescription = null,
                modifier = Modifier
                    .size(56.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(bounded = false),
                        onClick = onClick,
                    ),
                tint = contentColor,
            )
        }
    }
}


fun Modifier.animatedGradient(animating: Boolean): Modifier = composed {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(rotation, animating) {
        if (!animating) return@LaunchedEffect
        val target = rotation.value + 360f
        rotation.animateTo(
            targetValue = target,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 30_000,
                    easing = LinearEasing,
                ),
            ),
        )
    }

    drawWithCache {
        val rectSize = sqrt(size.width * size.width + size.height * size.height)
        val topLeft = Offset(
            x = -(rectSize - size.width) / 2,
            y = -(rectSize - size.height) / 2,
        )

        val brush1 = Brush.linearGradient(
            0f to Color.Magenta,
            1f to Color.Cyan,
            start = topLeft,
            end = Offset(rectSize * 0.7f, rectSize * 0.7f),
        )

        val brush2 = Brush.linearGradient(
            0f to Color(0xFF9E03FF),
            1f to Color(0xFF11D36E),
            start = Offset(rectSize, 0f),
            end = Offset(0f, rectSize),
        )

        val maskBrush = Brush.linearGradient(
            0f to Color.White,
            1f to Color.Transparent,
            start = Offset(rectSize / 2f, 0f),
            end = Offset(rectSize / 2f, rectSize),
        )

        onDrawBehind {
            val value = rotation.value

            withTransform(transformBlock = { rotate(value) }) {
                drawRect(
                    brush = brush1,
                    topLeft = topLeft,
                    size = Size(rectSize, rectSize),
                )
            }

            withTransform(transformBlock = { rotate(-value) }) {
                drawRect(
                    brush = maskBrush,
                    topLeft = topLeft,
                    size = Size(rectSize, rectSize),
                    blendMode = BlendMode.DstOut,
                )
            }

            withTransform(transformBlock = { rotate(value) }) {
                drawRect(
                    brush = brush2,
                    topLeft = topLeft,
                    size = Size(rectSize, rectSize),
                    blendMode = BlendMode.DstAtop,
                )
            }
        }
    }
}
