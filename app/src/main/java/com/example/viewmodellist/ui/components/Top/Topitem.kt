package com.example.viewmodellist.ui.components.top


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.utils.Datamodels.TopSongItem


@Composable
fun Topitem(
    title: String,
    updatetime: String = "",
    topid: Long,
    topimg: String = "",
    findviewModel: FindviewModel,
    modifier: Modifier = Modifier,
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0, 0, 0, 0)

        ),
        modifier = Modifier.padding(bottom = 10.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(topimg),
                modifier = Modifier.size(115.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Text(
                text = updatetime,
                fontSize = 11.sp,
                color = Color.White.copy(0.5f),
                modifier = Modifier
                    .align(BottomCenter)
            )
        }


    }
}


@Preview
@Composable
fun TopitemPreview() {

    Topitem(
        "云音乐说唱榜", "",
        424,
        "",
        FindviewModel(),
    )
}

