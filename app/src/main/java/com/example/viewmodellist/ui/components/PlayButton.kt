package com.example.viewmodellist.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButtonDefaults

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.find.MusicPlayer

@Composable
fun PlayButton(onClick: () -> Unit = {}, extended: Boolean, modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    FloatingActionButton(
        onClick = onClick,
        modifier = modifier,
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
            if (extended)
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_left_24),
                    contentDescription = null,
                    modifier = Modifier.size(35.dp)
                )
            else {
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
                            painter = rememberAsyncImagePainter("https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg"),
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(35.dp)
                            .align(Alignment.Center)
                    )
                }

            }

            AnimatedVisibility(
                visible = extended,
            ) {
                MusicPlayer(url = "http://m8.music.126.net/20230725192642/60423efede2a309bcb1541641a924a36/ymusic/397d/2039/9488/c3500e9cfdfd0bf3a0e15e04413c8deb.mp3")
            }
        }
    }
}


@Preview
@Composable
fun PlayButtonPreview() {
    PlayButton(onClick = {}, true)
}

