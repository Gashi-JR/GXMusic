package com.example.viewmodellist.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.utils.formatter
import java.nio.file.WatchEvent


@Composable
fun SongCover(
    imageUrl: String,
    title: String,
    intro: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .clip(shape = MaterialTheme.shapes.small)

    ) {

        Card(shape = MaterialTheme.shapes.small) {

            Image(
                painter = rememberAsyncImagePainter(imageUrl),
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    maxLines = 1,
                    modifier = Modifier.width(120.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = intro,
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(120.dp),
                    color = Color.Gray
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.baseline_smart_display_24),
                contentDescription = null,
                tint = Color.Black.copy(alpha = 0.2f),

                )
        }

    }
}


@Preview
@Composable
fun SongcoverPreview() {
    SongCover(
        "http://pic-bucket.ws.126.net/photo/0003/2021-11-16/GOTKEOOU00AJ0003NOS.jpg",
        "阿发发发疯阿华",
        "adadadadw"
    )
}