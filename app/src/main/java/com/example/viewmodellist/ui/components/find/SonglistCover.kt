package com.example.viewmodellist.ui.components.find


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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


@Composable
fun SonglistCover(
    imageUrl: String,
    title: String,
    playCount: Long,
    copywriter: String?,
    onClick: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .width(120.dp)

    ) {
        Row {
            Card(shape = MaterialTheme.shapes.small) {

                Box {

                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        modifier = modifier
                            .size(120.dp)
                            .clickable(onClick = {
                                onClick()
                            }),
                        contentScale = ContentScale.Crop,
                        contentDescription = null
                    )
                    if (!copywriter.isNullOrBlank()) {
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
            modifier = Modifier
                .width(120.dp)
                .pointerInput(Unit) {
                    detectTapGestures(
                        // 长按事件
                        onLongPress = {},
                        // 点击事件
                        onTap = {
                            onClick()
                        })
                }
        )


    }
}


@Preview(showBackground = true)
@Composable
fun SonglistcoverPreview() {
    SonglistCover(
        "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
        "阿发发发疯阿发复旦复华",
        6346363636,
        "播放过万"
    )
}