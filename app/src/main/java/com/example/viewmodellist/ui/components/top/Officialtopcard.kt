package com.example.viewmodellist.ui.components.top


import android.annotation.SuppressLint
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomEnd
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
import com.example.viewmodellist.utils.Datamodels.TopSongItem


@Composable
fun Officialtopcard(
    title: String,
    updatetime: String = "",
    topimg: String = "",
    topsong: List<TopSongItem>,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .width(480.dp)
            .height(180.dp)
            .background(Color(0, 0, 0, 0)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.7.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = CenterVertically
            ) {
                Row(verticalAlignment = CenterVertically) {
                    Text(
                        text = title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = updatetime,
                    fontSize = 14.sp,
                    color = Color.Gray
                )

            }
            Row(
                verticalAlignment = CenterVertically,

                ) {
                Card(
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0, 0, 0, 0)

                    ),
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(topimg),
                            modifier = Modifier.size(100.dp),
                            contentScale = ContentScale.Crop,
                            contentDescription = null
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.side_music_line),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(BottomEnd)
                                .size(24.dp)
                        )
                    }


                }
                Column(modifier = Modifier.padding(end = 15.dp)) {
                    if (topsong.isNotEmpty())
                        topsong.forEachIndexed { index, item ->
                            Row(
                                modifier = Modifier
                                    .height(30.dp)
                                    .fillMaxWidth()
                                    .clip(shape = MaterialTheme.shapes.small),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)

                            ) {


                                Icon(
                                    painter = painterResource(id = if (index == 0) R.drawable.one else if (index == 1) R.drawable.two else R.drawable.three),
                                    contentDescription = null,
                                    tint = when (index) {
                                        0 -> Color(
                                            255,
                                            215,
                                            0
                                        )

                                        1 -> Color(
                                            192,
                                            192,
                                            192
                                        )

                                        else -> Color(205, 133, 63)
                                    },
                                    modifier = Modifier.size(15.dp)
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxHeight(),
                                    verticalAlignment = CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Text(
                                        text = item.name,
                                        fontSize = 16.sp,
                                        maxLines = 1,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = " - ${item.artist}",
                                        fontSize = 14.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1,
                                        color = Color.Gray
                                    )


                                }

                            }

                        }
                    else
                        LoadingAnimation(
                            modifier = modifier
                                .height(90.dp)
                                .fillMaxWidth()
                                .clip(shape = MaterialTheme.shapes.small)
                        )

                }

            }

            Spacer(modifier = Modifier.height(20.dp))

        }
    }

}


@Preview
@Composable
fun OfficialtopcardPreview() {

    Officialtopcard(
        "云音乐说唱榜", "",
        "",
        listOf(
            TopSongItem(15, "adad", "ada", "adad"),
            TopSongItem(15, "adad", "ada", "adad"),
            TopSongItem(15, "adad", "ada", "adad")
        ),
    )
}

