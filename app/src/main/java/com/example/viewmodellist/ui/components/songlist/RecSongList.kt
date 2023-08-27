package com.example.viewmodellist.ui.components.find


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.viewmodellist.ui.components.Tag
import com.example.viewmodellist.utils.formatter


//TODO 传入专辑信息，显示效果

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecSongList(
    imageUrl: String,
    title: String,
    playCount: Long,
    id: Long,
    copywriter: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .width(120.dp)

    ) {
        Row() {
            Card(shape = MaterialTheme.shapes.small) {

                Box {
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        modifier = modifier.size(120.dp),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    if (copywriter != "") {
                        Tag(
                            onClick = {},
                            modifier = Modifier
                                .height(14.dp)
                                .align(Alignment.BottomStart)
                        ) {
                            Text(
                                text = copywriter,
                                color = Color(250, 65, 64),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_play_arrow_40),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.BottomEnd)
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_play_arrow_15),
                            contentDescription = null,
                            tint = Color.White,

                            )
                        Text(
                            text = formatter.tranNumber(playCount, 0),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                    }

                }

            }

        }



        Text(
            text = title,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier.width(120.dp)
        )


    }
}


@Preview(showBackground = true)
@Composable
fun RecSongListPreview() {
    RecSongList(
        "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
        "阿发发发疯阿发复旦复华",
        6346363636,
        1515455,
        "播放过万"
    )
}