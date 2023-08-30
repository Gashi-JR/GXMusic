package com.example.viewmodellist.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


val GrayLight = Color(0xFFE8E6EC)

val cardGradient = Brush.horizontalGradient(
    colors = listOf(Color(217, 220, 253), Color(244, 216, 239)), // 渐变色列表
    startX = 0f,
    endX = 1000f
)
val borderGradient = Brush.horizontalGradient(
    colors = listOf(Color.Blue.copy(alpha = 0.15f), Color.Red.copy(alpha = 0.15f)), // 渐变色列表
    startX = 0f,
    endX = 1000f
)
val findcardGradient = Brush.verticalGradient(
    colors = listOf(Color(244, 216, 239), Color.White), // 渐变色列表
    startY = 0f,
    endY = 700f
)

val mylikemusicGradient = Brush.verticalGradient(
    colors = listOf(Color(253, 241, 241), Color.White), // 渐变色列表
    startY = 0f,
    endY = 450f
)
val recentmusicGradient = Brush.verticalGradient(
    colors = listOf(Color(254, 246, 233), Color.White), // 渐变色列表
    startY = 0f,
    endY = 450f
)
val vinylTimeMachineGradient = Brush.verticalGradient(
    colors = listOf(Color(242, 245, 250), Color.White), // 渐变色列表
    startY = 0f,
    endY = 450f
)

val topColor = Color(84, 4, 99, 255)
val bottomColor = Color(115, 7, 133, 255)
val lightBottom = Color(124, 62, 134, 255)
val songListGradient = Brush.verticalGradient(
    colors = listOf(topColor, bottomColor),
    //startY = 0f,
    //endY = 250f
)
val GrayGradient = Brush.verticalGradient(
    colors = listOf(Color.LightGray, Color.White),
)
