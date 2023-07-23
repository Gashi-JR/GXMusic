package com.example.viewmodellist.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.R

@Composable
fun LoadingAnimation(modifier: Modifier = Modifier) {
    val infinateTransition = rememberInfiniteTransition()
    val alpha by infinateTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 2000
                0.5f at 1000
            },
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        modifier = modifier
            .background(Color.Gray.copy(alpha = alpha))
    )
}


@Preview
@Composable
fun LoadingAnimationPreview() {
    LoadingAnimation()
}

