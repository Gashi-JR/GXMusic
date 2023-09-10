package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun BlurredImage(imageUrl: String, type: Int = 0, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageUrl) {
        withContext(Dispatchers.IO) {
            val requestOptions = RequestOptions.bitmapTransform(BlurTransformation(10, 8))

            val loadedBitmap = Glide.with(context)
                .asBitmap()
                .load(if (imageUrl != "" ) imageUrl else "https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png")
                .apply(requestOptions)
                .transition(BitmapTransitionOptions.withCrossFade())
                .submit()
                .get()

            bitmap.value = loadedBitmap.asImageBitmap()
        }
    }

    bitmap.let { imageBitmap ->
        imageBitmap.value?.let {
            Image(
                bitmap = it,
                contentDescription = null,
                modifier = modifier,
                contentScale = if (type == 0) ContentScale.FillWidth else ContentScale.FillHeight
            )
        }
    }
}

