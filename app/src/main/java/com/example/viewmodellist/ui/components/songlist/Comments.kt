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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.GrayLight
import com.example.viewmodellist.utils.Datamodels

//TODO传入歌单id展示所有评论
@Composable
fun Comments(
    songListViewModel: SongListViewModel,
    id : Long,
    modifier : Modifier= Modifier
){
    LaunchedEffect(songListViewModel.id){
        songListViewModel.fetchComments()
    }
    Column() {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
                    text = "评论(${songListViewModel.commentsData.size})",
                    modifier = modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.Bold
                )

            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(5.dp),){
            item{
                Column {
                    Row{
                        AsyncImage(model = songListViewModel.coverImgUrl.value,
                            contentDescription = "专辑封面",
                            modifier = modifier
                                .height(100.dp)
                                .width(100.dp)
                                .clip(MaterialTheme.shapes.extraLarge)
                                .padding(start = 4.dp, end = 4.dp, top = 10.dp, bottom = 4.dp))
                        Text(text = songListViewModel.name.value,
                            modifier = modifier.padding(top = 4.dp))
                    }
                    Box(modifier = modifier.fillMaxWidth()
                        .background(GrayLight)
                    ) {
                        Spacer(
                            modifier = modifier.height(6.dp)
                        )
                    }
                }

            }
            items(songListViewModel.commentsData){
                item->
                CommentItem(
                    picUrl = item.user.get("avatarUrl").asString,
                    nickname = item.user.get("nickname").asString,
                    content = item.content,
                    timeStr = item.timeStr,
                    ipLocation = item.ipLocation.get("location").asString)
            }
        }
    }
}

@Composable
fun CommentItem(
    picUrl : String,
    nickname : String,
    content : String,
    timeStr :String,
    ipLocation : String
){
    Row(modifier = Modifier.padding(start = 6.dp)){
        AsyncImage(model = picUrl,
            modifier = Modifier
                .clip(CircleShape)
                .height(40.dp)
                .width(40.dp),
            contentDescription = "头像")
        Column(modifier = Modifier.padding(start = 4.dp,top = 2.dp)) {
            Text(text = nickname,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp)
            Text(text = "$timeStr   ip:$ipLocation", fontSize = 12.sp)

            Spacer(modifier = Modifier.height(3.dp))
            Text(text = content)

            Spacer(modifier = Modifier.height(10.dp))
            Divider(modifier = Modifier.padding(horizontal = 16.dp),
            thickness = 1.dp)
        }
    }
}