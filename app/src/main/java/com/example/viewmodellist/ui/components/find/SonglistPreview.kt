package com.example.viewmodellist.ui.components.find


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Chip
import androidx.compose.material.ChipColors
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.utils.formatter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SonglistPreview(
    imageUrl: String,
    title: String,
    intro: String,
    tag: List<String>,
    modifier: Modifier = Modifier,

    ) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(0.3f)),
        modifier = modifier
            .padding(horizontal = 15.dp)
            .width(350.dp)
            .background(Color(0, 0, 0, 0)),
        shape = MaterialTheme.shapes.medium,
        // elevation = CardDefaults.elevatedCardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 15.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(shape = MaterialTheme.shapes.small) {

                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    modifier = modifier.size(300.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )

            }
            Text(
                text = title,
                fontSize = 14.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )

            Divider(
                thickness = 0.5.dp,
                color = Color.Red.copy(alpha = 0.15f),
                modifier = Modifier.padding(horizontal = 15.dp)
            )

        }
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "标签：", fontSize = 15.sp, color = Color.White)
                tag.forEach {
                    Chip(
                        onClick = {},
                        colors = ChipDefaults.chipColors(backgroundColor = Color.Gray.copy(alpha = 0.3f)),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = it, color = Color.White, fontSize = 13.sp)
                    }
                }

            }
            Text(
                text = "twte三个事实上事实上少时诵诗书是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒是撒个光个人各阿荣灌灌灌灌灌嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎嘎",
                fontSize = 13.sp,
                lineHeight = 17.sp,
                modifier = Modifier.fillMaxWidth(), color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

    }
}


@Preview
@Composable
fun SonglistPre() {

    SonglistPreview(
        "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
        "阿发发发疯阿发复旦复华",
        "aaa",
        listOf("aaa", "bbb")
    )
}

