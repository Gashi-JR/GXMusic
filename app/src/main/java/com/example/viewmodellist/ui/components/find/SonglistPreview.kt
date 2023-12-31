package com.example.viewmodellist.ui.components.find


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


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
            .height(630.dp)
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
                .fillMaxHeight()
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
                text = intro,
                fontSize = 13.sp,
                lineHeight = 17.sp,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(), color = Color.White
            )
        }


    }
}


@Preview
@Composable
fun SonglistPre() {

    SonglistPreview(
        "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
        "阿发发发疯阿发复旦复华",
        "阿发发发疯阿发复旦复华阿发发" +
                "阿发发发疯阿发复旦复华发疯阿发复旦\"阿发发发疯阿发复\"阿发发发疯阿发复旦复华发疯阿发复旦复华阿发发发疯阿发复旦复华阿发发发疯阿\"\"阿发发发疯阿发复旦复华发疯阿发复旦复华阿发发发疯阿发复旦复华阿发发发疯阿\"\"阿发发发疯阿发复旦复华发疯阿发复旦复华阿发发发疯阿发复旦复华阿发发发疯阿\"旦复华发疯阿发复旦复华阿发发发疯阿发复旦复华阿发发发疯阿\"复华阿发发发疯阿发复旦复华阿发发发疯阿" +
                "发复旦复华阿发发发疯阿发阿发发发疯阿发复旦复华复旦复华阿发发发疯阿发复旦复华",
        listOf("aaa", "bbb")
    )
}

