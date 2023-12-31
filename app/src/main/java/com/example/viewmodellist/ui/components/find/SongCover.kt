package com.example.viewmodellist.ui.components.find


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R


@Composable
fun SongCover(
    picUrl: String,
    name: String,
    artist: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .clip(shape = MaterialTheme.shapes.small)

    ) {

        Card(
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
        ) {

            Image(
                painter = rememberAsyncImagePainter(picUrl),
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(250.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = name,
                    fontSize = 16.sp,
                    maxLines = 1,
                    modifier = Modifier.width(120.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = artist,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(150.dp),
                    color = Color.Gray
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.video),
                contentDescription = null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp),
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun SongcoverPreview() {
    SongCover(
        "http://pic-bucket.ws.126.net/photo/0003/2021-11-16/GOTKEOOU00AJ0003NOS.jpg",
        "adadadadw",
        "adadad"
    )
}