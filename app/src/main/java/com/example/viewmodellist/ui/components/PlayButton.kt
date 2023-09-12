package com.example.viewmodellist.ui.components


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.dokar.amlv.LyricsViewState
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.find.MusicPlayer
import com.example.viewmodellist.ui.screens.find.FindviewModel
import kotlin.math.roundToInt

@Composable
fun PlayButton(
    onClick: () -> Unit = {},
    extended: Boolean,
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    state: LyricsViewState,
    modifier: Modifier = Modifier

) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )


    var offsetX by remember { mutableStateOf(20f) }
    var offsetY by remember { mutableStateOf(2325f) }
    FloatingActionButton(
        onClick = { },
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consumeAllChanges()
                    offsetX += dragAmount.x
                    offsetY += dragAmount.y
                }
            },
        shape = CircleShape,
        contentColor = Color.White,
        containerColor = Color.White,
        elevation = FloatingActionButtonDefaults.elevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(60.dp)
                    .graphicsLayer(
                        rotationZ = rotation
                    )
            ) {
                Card(
                    shape = CircleShape,
                    colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(findviewModel.currentMusic.value.picUrl),
                        modifier = Modifier
                            .size(60.dp)
                            .clickable { onClick() },
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.rotatemusic),
                    contentDescription = null,
                    modifier = Modifier
                        .size(35.dp)
                        .align(Alignment.Center),
                    tint = Color.Unspecified

                )

            }

            AnimatedVisibility(
                visible = extended,
            ) {
                MusicPlayer(
                    url = findviewModel.currentMusic.value.url,
                    findviewModel.currentMusic.value.name,
                    findviewModel.currentMusic.value.artist,
                    mediaPlayerViewModel,
                    state
                )
            }
        }
    }
}


@Preview
@Composable
fun PlayButtonPreview() {
    // PlayButton(onClick = {}, true, FindviewModel(), MediaPlayerViewModel())
}

