package com.example.viewmodellist.ui.components.songlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.songListGradient
import com.google.accompanist.pager.ExperimentalPagerApi
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.GrayGradient
import com.example.viewmodellist.ui.theme.GrayLight
import com.example.viewmodellist.ui.theme.bottomColor
import com.example.viewmodellist.ui.theme.lightBottom
import com.example.viewmodellist.ui.theme.topColor

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun SongListDetail(
    loginviewModel : LoginviewModel = LoginviewModel(),
    songListViewModel : SongListViewModel = SongListViewModel(),
    mediaPlayerViewModel : MediaPlayerViewModel = MediaPlayerViewModel(),
    findviewModel: FindviewModel = FindviewModel(),
    modifier : Modifier = Modifier
    ) {

    var coverImgUrl by remember { mutableStateOf(songListViewModel.coverImgUrl) }
    LaunchedEffect(loginviewModel.result.value.cookie) {
        songListViewModel.fetchSongLists()
    }

    AnimatedVisibility(visible = !songListViewModel.isShowComments.value) {
        //TODO : 歌单部分
        Column() {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(topColor),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(onClick = {
                        songListViewModel.isShowDetail.value =
                            !songListViewModel.isShowDetail.value
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = "歌单",
                        color = Color.White,
                        modifier = modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Bold
                    )

                }
                Row(verticalAlignment = Alignment.CenterVertically,) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "search",
                        tint = Color.White
                    )
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_more_vert_24),
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.background(Color.White)
            ) {
                item {
                    Column(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(songListGradient)
                    ) {

                        Spacer(modifier = modifier.height(25.dp))

                        Row() {
                            AsyncImage(
                                model = coverImgUrl.value,
                                contentDescription = "专辑封面",
                                modifier = modifier
                                    .height(100.dp)
                                    .width(100.dp)
                                    .padding(start = 8.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )

                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = songListViewModel.name.value,
                                    modifier = Modifier.padding(start = 8.dp),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(modifier = Modifier.padding(start = 8.dp,top = 4.dp)) {

                                    AsyncImage(model = songListViewModel.userAvatar.value,
                                        contentDescription = "用户头像",
                                    modifier = modifier
                                        .clip(CircleShape)
                                        .height(30.dp)
                                        .width(30.dp))

                                    Text(
                                        text = "gnon2002",
                                        modifier = modifier.padding(top = 5.dp, start = 6.dp),
                                        color = GrayLight,
                                        fontWeight = FontWeight.Light
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                                        contentDescription = null,
                                        tint = Color.Gray.copy(alpha = 0.5f),
                                        modifier = modifier.padding(top=2.dp)
                                    )
                                }
                            }
                        }

                        //.border(20.dp,Color.Black, RoundedCornerShape(20.dp,20.dp,0.dp,0.dp)
                        Spacer(modifier = Modifier.height(50.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            androidx.compose.material.Button(
                                onClick = { /*TODO*/ },
                                modifier = modifier.background(Color.Transparent),
                                colors = ButtonDefaults.buttonColors(lightBottom)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_screen_share_24),
                                    contentDescription = "分享"
                                )
                                Text(text = "分享",
                                    modifier=modifier.padding(start = 2.dp)
                                )
                            }

                            androidx.compose.material.Button(
                                onClick = { songListViewModel.isShowComments.value = !songListViewModel.isShowComments.value },
                                modifier = modifier.background(Color.Transparent),
                                colors = ButtonDefaults.buttonColors(lightBottom)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_comment_24),
                                    contentDescription = "评论"
                                )
                                Text(text = "评论",
                                    modifier=modifier.padding(start = 2.dp)
                                )
                            }
                            androidx.compose.material.Button(
                                onClick = { songListViewModel.collectPlayList() },
                                colors = ButtonDefaults.buttonColors(lightBottom)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_create_new_folder_24),
                                    contentDescription = "收藏"
                                )
                                Text(text = "收藏",
                                    modifier=modifier.padding(start = 2.dp)
                                )
                            }
                        }
                        Spacer(modifier = modifier.height(20.dp))
                        Text(
                            text = "",
                            modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .clip(RoundedCornerShape(20.dp, 20.dp))
                                .background(GrayGradient)
                                .padding(top = 20.dp)
                        )
                        Divider(thickness = 0.5.dp,
                        color = Color.LightGray)
                    }
                }
                itemsIndexed(songListViewModel.songList) { index, songlist ->
                    SongItem(
                        index,
                        songlist.name,
                        songlist.artist,
                        fee = songlist.fee,
                        modifier = Modifier.clickable {
                            findviewModel.currentMusic.value.id = songlist.id
                            findviewModel.currentMusic.value.name = songlist.name
                            findviewModel.currentMusic.value.picUrl = songListViewModel.coverImgUrl.value
                            findviewModel.currentMusic.value.artist = songlist.artist
                            songListViewModel.selectedSongIndex.value = index
                            mediaPlayerViewModel.play(songlist.url)
                            println("当前选中音乐的URL:" + songlist.url)
                        },
                        isSelected = index == songListViewModel.selectedSongIndex.value)
                }
            }
        }
    }
    AnimatedVisibility(visible = songListViewModel.isShowComments.value) {
        Comments(songListViewModel,songListViewModel.id.value)
    }

}


@Composable
fun SongItem(
    no : Int,
    name : String,
    author : String,
    fee : Int,
    modifier: Modifier = Modifier,
    isSelected : Boolean
){
    val songTextStyle = if (isSelected) {
        TextStyle(
            color = Color.Red
        )
    } else {
        TextStyle()
    }
    Row(modifier = modifier
        .fillMaxWidth()
        .background(Color.White),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {
            Text(
                text = (no+1).toString(),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
                style = songTextStyle
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = name,
                    style = songTextStyle
                )
                Row() {

                    if(fee == 1) {
                        Box(
                            modifier = modifier
                                .padding(top = 5.dp)
                                .background(Color.White)
                                .drawBehind {
                                    // 绘制圆角矩形边界线
                                    drawRoundRect(
                                        color = Color(250, 65, 64),
                                        size = size,
                                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                                    )
                                }
                        ) {
                            Text(
                                text = "VIP",
                                fontSize = 10.sp,
                                color = Color.White,
                            )
                        }
                    }else{
                        Box(
                            modifier = modifier
                                .padding(top = 5.dp)
                                .background(Color.White)
                                .drawBehind {
                                    // 绘制圆角矩形边界线
                                    drawRoundRect(
                                        color = Color(192, 142, 68, 255),
                                        size = size,
                                        cornerRadius = CornerRadius(4.dp.toPx(), 4.dp.toPx())
                                    )
                                }
                        ) {
                            Text(
                                text = "免费",
                                fontSize = 10.sp,
                                color = Color.White,
                            )
                        }
                    }

                    Text(text = author, fontWeight = FontWeight.Light, color = Color(
                        121,
                        119,
                        117,
                        255
                    ))
                }
            }
        }


        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "",
                tint = Color.Black.copy(alpha = 0.5f)
            )
        }
    }
    
    
}

@Preview
@Composable
fun SonglistPreview() {
    ViewModelListTheme {
        SongListDetail()
    }
}