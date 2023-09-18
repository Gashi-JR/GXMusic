package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.GrayLight
import com.example.viewmodellist.utils.formatter

//TODO 传入歌单id展示所有评论
@Composable
fun Comments(
    songListViewModel: SongListViewModel,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(songListViewModel.id) {
        if (songListViewModel.commentsData.isEmpty())
            songListViewModel.fetchComments()
    }
    Column {

        Spacer(
            modifier = Modifier
                .height(formatter.mainActivity?.getStatusBarHeight()!!.dp)
                .fillMaxWidth()
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    songListViewModel.isShowComments.value = !songListViewModel.isShowDetail.value
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = null
                    )
                }
                Text(
                    text = "精选评论(${songListViewModel.shareCount.value})",
                    modifier = modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.Bold
                )

            }
        }


        LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            item {
                Column(modifier = modifier.padding(start = 8.dp)) {
                    Row(horizontalArrangement = Arrangement.Center) {
                        AsyncImage(
                            model = songListViewModel.coverImgUrl.value,
                            contentDescription = "专辑封面",
                            modifier = modifier
                                .size(120.dp)
                                .clip(RoundedCornerShape(15.dp))
                        )
                        Column(verticalArrangement = Arrangement.Center,
                        modifier = modifier.padding(start = 8.dp,top = 18.dp)) {
                            Text(
                                text = "${songListViewModel.name.value}",
                                modifier = modifier
                                    .padding(top = 4.dp)
                            )
                            Row(){
                                Text(text = "by  ",fontSize = 11.sp,modifier=modifier.padding(top=4.dp))
                                Text(text = "${songListViewModel.userName.value}",
                                    fontSize = 11.sp,
                                    color = Color.Blue,
                                    modifier = modifier
                                        .padding(top = 4.dp))
                            }
                        }
                    }
                    Spacer(modifier = modifier.height(12.dp))
                    Box(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(GrayLight)
                    ) {
                        Spacer(modifier = modifier.height(6.dp))
                    }
                }

            }
            if (songListViewModel.commentsData.isNotEmpty()) {
                itemsIndexed(
                    songListViewModel.commentsData,
                ) { _, item ->
                    CommentItem(
                        picUrl = item.user.get("avatarUrl").asString,
                        nickname = item.user.get("nickname").asString,
                        content = item.content,
                        timeStr = item.timeStr,
                        ipLocation = item.ipLocation.get("location").asString
                    )
                }
                item {
                    Spacer(modifier = Modifier.height(70.dp))
                }
            } else {
                items(List(6) { 1 }) {
                    LoadingAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(10.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                }
            }
        }
    }
}

@Composable
fun CommentItem(
    picUrl: String,
    nickname: String,
    content: String,
    timeStr: String,
    ipLocation: String
) {
    Row(
        modifier = Modifier.padding(start = 6.dp)
    ) {
        AsyncImage(
            model = picUrl,
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            contentDescription = "头像",
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(start = 4.dp, top = 2.dp)) {
            Text(
                text = nickname,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Text(text = "$timeStr   ip:$ipLocation", fontSize = 12.sp)

            Spacer(modifier = Modifier.height(3.dp))
            Text(text = content,fontSize = 15.sp)

            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                modifier = Modifier.padding(horizontal = 15.dp),
                thickness = 0.15.dp,
                color = Color.Gray.copy(0.2f)
            )
        }
    }
}