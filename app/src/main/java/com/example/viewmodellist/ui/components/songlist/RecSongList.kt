package com.example.viewmodellist.ui.components.songlist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.SonglistCover
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel

//TODO 歌单广场的主页面

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RecSongList(
    songListViewModel: SongListViewModel = SongListViewModel(),
    loginviewModel: LoginviewModel,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {


    //需要滚动
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        //专属的推荐歌单 一共六个推荐（3*2）
        item {
            Row {

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = modifier.padding(horizontal = 15.dp)
                ) {
                    Text(
                        text = "Hi ${loginviewModel.User.value.nickname}，快来听听",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.padding(start = 5.dp, bottom = 5.dp)
                    )
                    if (songListViewModel.songlistData.isNotEmpty()) {
                        FlowRow(
                            maxItemsInEachRow = 3,
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            songListViewModel.songlistData.forEach { item ->
                                AlbumArt(
                                    id = item.id,
                                    imageUrl = item.picUrl,
                                    title = item.name,
                                    playCounts = item.playCount,
                                    songListViewModel = songListViewModel
                                )
                            }
                        }
                    } else {
                        FlowRow(
                            maxItemsInEachRow = 3,
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            List(6) { 1 }.forEach { _ ->
                                Column {
                                    LoadingAnimation(
                                        modifier = Modifier
                                            .fillMaxWidth(0.3f)
                                            .aspectRatio(1f)
                                            .clip(MaterialTheme.shapes.small)
                                    )
                                    Spacer(modifier = Modifier.height(5.dp))
                                }

                            }
                        }
                    }
                }
            }
        }
        item{

            Text(
                text = "今日达人推荐",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(start = 15.dp, bottom = 5.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(horizontal = 15.dp),
                modifier = Modifier.height(160.dp)
            ) {
                if (songListViewModel.hotPlayList.isNotEmpty()) {
                    items(
                        songListViewModel.hotPlayList.take(6),
                        key = { item -> item.id }) { item ->
                        SonglistCover(
                            imageUrl = item.coverImgUrl,
                            title = item.name,
                            playCount = if (item.playCount > 0) item.playCount else item.playCount,
                            copywriter = null,
                            onClick = {
                                songListViewModel.detailId.value = item.id
                                songListViewModel.fetchSongLists()
                                songListViewModel.isShowDetail.value =
                                    true
                                songListViewModel.des.value = ""
                            },
                        )
                    }
                } else {
                    items(List(4) { 1 }) {
                        LoadingAnimation(
                            modifier = Modifier
                                .width(120.dp)
                                .height(160.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                }
            }

        }
        item(key = 2) {

            Text(
                text = if (songListViewModel.currentTime.value < 8) {
                    stringResource(id = R.string.greet_morning)
                } else if (songListViewModel.currentTime.value < 14) {
                    stringResource(id = R.string.greet_noon)
                } else if (songListViewModel.currentTime.value < 19) {
                    stringResource(id = R.string.greet_afternoon)
                } else {
                    stringResource(id = R.string.greet_night)
                },
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 5.dp, start = 15.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.Top,
                contentPadding = PaddingValues(horizontal = 15.dp),
                modifier = Modifier.height(160.dp)
            ) {
                if (songListViewModel.hotPlayList.isNotEmpty()) {
                    items(
                        songListViewModel.hotPlayList.subList(6, 12),
                        key = { item -> item.id }) { item ->
                        SonglistCover(
                            imageUrl = item.coverImgUrl,
                            title = item.name,
                            playCount = if (item.playCount > 0) item.playCount else item.playCount,
                            copywriter = null,
                            onClick = {
                                songListViewModel.detailId.value = item.id
                                songListViewModel.fetchSongLists()
                                songListViewModel.isShowDetail.value =
                                    true
                                songListViewModel.des.value = ""
                            },
                        )
                    }
                } else {
                    items(List(4) { 1 }) {
                        LoadingAnimation(
                            modifier = Modifier
                                .width(120.dp)
                                .height(160.dp)
                                .clip(MaterialTheme.shapes.small)
                        )
                    }
                }
            }
        }
        item{
            Text(
                text = "这些歌单，你一定在找",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 5.dp, start = 15.dp)
            )

            Column(
                modifier = modifier.padding(horizontal = 15.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (songListViewModel.hotPlayList.isNotEmpty()) {
                    FlowRow(
                        maxItemsInEachRow = 3,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        songListViewModel.hotPlayList.subList(
                            12,
                            songListViewModel.hotPlayList.size
                        ).forEach { item ->
                            AlbumArt(
                                id = item.id,
                                imageUrl = item.coverImgUrl,
                                title = item.name,
                                playCounts = item.playCount,
                                songListViewModel = songListViewModel
                            )
                        }
                    }
                } else {
                    FlowRow(
                        maxItemsInEachRow = 3,
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        List(12) { 1 }.forEach { _ ->
                            Column {
                                LoadingAnimation(
                                    modifier = Modifier
                                        .fillMaxWidth(0.3f)
                                        .aspectRatio(1f)
                                        .clip(MaterialTheme.shapes.small)
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                            }

                        }
                    }

                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(55.dp))
        }
    }

}