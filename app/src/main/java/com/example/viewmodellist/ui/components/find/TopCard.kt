package com.example.viewmodellist.ui.components.find


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.utils.Datamodels.TopSongItem
import kotlinx.coroutines.launch


@Composable
fun TopCard(
    title: String,
    topid: Long,
    topsong: List<TopSongItem>,
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    modifier: Modifier = Modifier,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .padding(horizontal = 15.dp)
            .width(350.dp)
            .height(380.dp)
            .background(Color(0, 0, 0, 0)),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 1.5.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                        contentDescription = title,
                        tint = Color.Black.copy(alpha = 0.5f)
                    )
                }
                Text(
                    text = "大家都在听",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

            }
            Divider(
                thickness = 0.5.dp,
                color = Color.Red.copy(alpha = 0.15f),
                modifier = Modifier.padding(horizontal = 15.dp)
            )
            val scope = rememberCoroutineScope() // 获取关联的协程作用域
            if (topsong.isNotEmpty())
                topsong.forEachIndexed { index, item ->
                    Row(
                        modifier = modifier
                            .height(90.dp)
                            .padding(15.dp)
                            .fillMaxWidth()
                            .clip(shape = MaterialTheme.shapes.small)
                            .clickable {

                                scope.launch {
                                    findviewModel.currentMusic.value.id = item.id
                                    findviewModel.currentMusic.value.name = item.name
                                    findviewModel.currentMusic.value.picUrl = item.picUrl
                                    findviewModel.currentMusic.value.artist = item.artist
                                    findviewModel.fetchCurrentMusicUrl(item.id)
                                    mediaPlayerViewModel.play(
                                        Repository().getCurrentMusicUrl(
                                            item.id
                                        )
                                    )
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)

                    ) {

                        Card(
                            shape = MaterialTheme.shapes.small,
                            colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                        ) {

                            Image(
                                painter = rememberAsyncImagePainter(item.picUrl),
                                modifier = Modifier.size(60.dp),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )


                        }

                        Icon(
                            painter = painterResource(id = if (index == 0) R.drawable.one else if (index == 1) R.drawable.two else R.drawable.three),
                            contentDescription = null,
                            tint = if (index == 0) Color(255, 215, 0) else if (index == 1) Color(
                                192,
                                192,
                                192
                            ) else Color(205, 133, 63),
                            modifier = Modifier.size(15.dp)
                        )
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = item.name,
                                fontSize = 16.sp,
                                maxLines = 1,
                                modifier = Modifier.width(120.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))
                            Text(
                                text = item.artist,
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.width(120.dp),
                                color = Color.Gray
                            )


                        }

                    }

                }
            else
                listOf(1, 2, 3).forEach { _ ->
                    LoadingAnimation(
                        modifier = modifier
                            .height(90.dp)
                            .padding(15.dp)
                            .fillMaxWidth()
                            .clip(shape = MaterialTheme.shapes.small)
                    )
                }


            Spacer(modifier = Modifier.height(20.dp))

        }
    }

}


@Preview
@Composable
fun TopCardPreview() {

    TopCard(
        "云音乐说唱榜",
        424,
        listOf(
            TopSongItem(15, "adad", "ada", "adad"),
            TopSongItem(15, "adad", "ada", "adad"),
            TopSongItem(15, "adad", "ada", "adad")
        ),
        FindviewModel(),
        MediaPlayerViewModel(),
    )
}

