package com.example.viewmodellist.ui.components.songlist

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.GrayLight
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.utils.formatter


@Composable
fun SongListDetail(
    loginviewModel: LoginviewModel = LoginviewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    findviewModel: FindviewModel = FindviewModel(),
    @SuppressLint("ModifierParameter")
    modifier: Modifier = Modifier
) {

    val coverImgUrl by remember { mutableStateOf(songListViewModel.coverImgUrl) }
    LaunchedEffect(loginviewModel.result.value.cookie) {
        songListViewModel.fetchSongLists()
    }

    AnimatedVisibility(visible = !songListViewModel.isShowComments.value) {
        //TODO : 歌单部分


        LazyColumn(
            modifier = Modifier.background(Color.White)
        ) {
            item(key = 0) {
                Box(
                    modifier = Modifier

                ) {
                    BlurredImage(
                        imageUrl = coverImgUrl.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height((245 + formatter.mainActivity?.getStatusBarHeight()!!).dp)
                    )

                    Column(
                        modifier = modifier
                    ) {
                        Spacer(
                            modifier = Modifier
                                .height(formatter.mainActivity?.getStatusBarHeight()!!.dp)
                                .fillMaxWidth()
                        )
                        IconButton(onClick = {
                            songListViewModel.isShowDetail.value =
                                !songListViewModel.isShowDetail.value
                            songListViewModel.onBack.value()
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Row(modifier = Modifier.padding(horizontal = 15.dp)) {

                            AsyncImage(
                                model = coverImgUrl.value,
                                contentDescription = "专辑封面",
                                modifier = modifier
                                    .height(110.dp)
                                    .width(110.dp)
                                    .clip(MaterialTheme.shapes.small)
                                    .clickable { }
                            )

                            Column(
                                modifier = Modifier.padding(7.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = songListViewModel.name.value,
                                    // modifier = Modifier.padding(start = 8.dp),
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Row(
                                    // modifier = Modifier.padding(top = 15.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    AsyncImage(
                                        model = songListViewModel.userAvatar.value,
                                        contentDescription = "用户头像",
                                        modifier = modifier
                                            .clip(CircleShape)
                                            .height(30.dp)
                                            .width(30.dp)
                                    )

                                    Text(
                                        text = songListViewModel.userName.value,
                                        modifier = modifier.padding(horizontal = 6.dp),
                                        color = GrayLight,
                                        fontWeight = FontWeight.Light
                                    )
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                                        contentDescription = null,
                                        tint = Color.White.copy(alpha = 0.5f),
                                    )
                                }
                                if (songListViewModel.des.value != "")
                                    Text(
                                        text = songListViewModel.des.value,
                                        // modifier = Modifier.padding(start = 8.dp),
                                        color = Color.White.copy(0.5f),
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        fontSize = 13.sp
                                    )
                            }
                        }

                        Spacer(modifier = modifier.height(10.dp))


                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                        ) {
                            androidx.compose.material.Button(
                                onClick = { /*TODO*/ },
                                modifier = modifier
                                    .background(Color.Transparent)
                                    .weight(1f),
                                shape = MaterialTheme.shapes.extraLarge,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Gray.copy(
                                        0.3f
                                    )
                                ),
                                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.share),
                                    contentDescription = "分享",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = formatter.tranNumber(
                                        songListViewModel.shareCount.value.toLong(),
                                        0
                                    ),
                                    modifier = modifier.padding(start = 2.dp),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            androidx.compose.material.Button(
                                onClick = {
                                    songListViewModel.isShowComments.value =
                                        !songListViewModel.isShowComments.value
                                },
                                modifier = modifier
                                    .background(Color.Transparent)
                                    .weight(1f),
                                shape = MaterialTheme.shapes.extraLarge,
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Gray.copy(
                                        0.3f
                                    )
                                ),
                                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.comments),
                                    contentDescription = "评论",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = formatter.tranNumber(
                                        songListViewModel.commentCount.value.toLong(),
                                        0
                                    ),
                                    modifier = modifier.padding(start = 2.dp),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            androidx.compose.material.Button(
                                onClick = { songListViewModel.collectPlayList() },
                                shape = MaterialTheme.shapes.extraLarge,
                                modifier = modifier
                                    .background(Color.Transparent)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Gray.copy(
                                        0.3f
                                    )
                                ),
                                elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.sub),
                                    contentDescription = "收藏",
                                    modifier = Modifier.size(24.dp),
                                    tint = Color.White
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Text(
                                    text = formatter.tranNumber(
                                        songListViewModel.bookedCount.value.toLong(),
                                        0
                                    ),
                                    modifier = modifier.padding(start = 2.dp),
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(modifier = modifier.height(10.dp))
                        Text(
                            text = "",
                            modifier
                                .fillMaxWidth()
                                .height(20.dp)
                                .clip(RoundedCornerShape(20.dp, 20.dp))
                                .background(Color.White)
                                .padding(top = 20.dp)
                        )

                    }
                }
            }
            if (songListViewModel.songList.isNotEmpty()) {
                itemsIndexed(
                    songListViewModel.songList,
                    key = { _, item -> item.id }) { index, songlist ->
                    SongItem(
                        index,
                        songlist.name,
                        songlist.artist,
                        fee = songlist.fee,
                        modifier = Modifier.clickable {
                            findviewModel.currentMusic.value.id = songlist.id
                            findviewModel.currentMusic.value.name = songlist.name
                            findviewModel.currentMusic.value.picUrl =
                                songListViewModel.coverImgUrl.value
                            findviewModel.currentMusic.value.artist = songlist.artist
                            songListViewModel.selectedSongIndex.value = index
                            mediaPlayerViewModel.play(songlist.url)
                            println("当前选中音乐的URL:" + songlist.url)
                        },
                        isSelected = index == songListViewModel.selectedSongIndex.value
                    )
                }
                item(key = 1) {
                    Spacer(modifier = Modifier.height(55.dp))
                }
            } else {
                items(List(6) { 1 }) {
                    LoadingAnimation(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .padding(15.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                }
            }

        }

    }
    AnimatedVisibility(visible = songListViewModel.isShowComments.value) {
        Comments(songListViewModel)
    }

}


@Composable
fun SongItem(
    no: Int,
    name: String,
    author: String,
    fee: Int,
    modifier: Modifier = Modifier,
    isSelected: Boolean
) {
    val songTextStyle = if (isSelected) {
        TextStyle(
            color = Color(250, 65, 64)
        )
    } else {
        TextStyle(color = Color.Black)
    }

    val songTextGrayStyle = if (isSelected) {
        TextStyle(
            color = Color(250, 65, 64)
        )
    } else {
        TextStyle(color = Color.Gray)
    }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {
            Text(
                text = (no + 1).toString(),
                modifier = Modifier
                    .padding(start = 12.dp)
                    .width(25.dp)
                    .align(Alignment.CenterVertically),

                style = songTextGrayStyle,
                textAlign = TextAlign.Center
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(
                    text = name,
                    style = songTextStyle,
                    fontSize = 16.sp,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    when (fee) {
                        1 -> {
                            Box(
                                modifier = modifier
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
                        }

                        -1 -> {
                        }

                        else -> {
                            Box(
                                modifier = modifier
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
                    }
                    Text(
                        text = author, fontWeight = FontWeight.Light,
                        fontSize = 12.sp,
                        style = songTextGrayStyle
                    )
                }
            }
        }


        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "",
                tint = Color.Black.copy(alpha = 0.2f)
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