package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.AlbumArt
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.find.SonglistCover
import com.example.viewmodellist.ui.components.find.TopCard
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel

//TODO 歌单广场的主页面

@Composable
fun RecSongList(

    findviewModel : FindviewModel = FindviewModel(),
    mediaPlayerViewModel : MediaPlayerViewModel = MediaPlayerViewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    modifier: Modifier = Modifier
) {


    //需要滚动
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        //专属的推荐歌单 一共六个推荐（3*2）
        item {
            Row {
                if (songListViewModel.songlistData.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "Hi gnon2002，快来听听",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = modifier.padding(bottom = 5.dp)
                        )

                        Spacer(modifier = modifier.height(5.dp))
                        
                        RowThree1(albumArts = songListViewModel.songlistData.subList(0, 3), songListViewModel = songListViewModel)
                        RowThree1(albumArts = songListViewModel.songlistData.subList(3, 6), songListViewModel = songListViewModel)
                    }
                } else
                    println("失败捏")
            }
        }
        item {

            Text(
                text = "今日达人推荐",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 5.dp)
            )
            if (songListViewModel.hotPlayList.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(songListViewModel.hotPlayList.take(6)) { item ->
                        AlbumArt(
                            id = item.id,
                            imageUrl = item.coverImgUrl,
                            title = item.name,
                            playCounts = item.playCount,
                            songListViewModel = songListViewModel
                        )
                    }
                }
            } else
                println("等待")
        }
        item {
            Text(
                text = "日落与音乐相伴 睡个好觉 清晨和旋律相遇 午间律动",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 5.dp)
            )
            if (songListViewModel.hotPlayList.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(songListViewModel.hotPlayList.subList(6, 12)) { item ->
                        AlbumArt(
                            id = item.id,
                            imageUrl = item.coverImgUrl,
                            title = item.name,
                            playCounts = item.playCount,
                            songListViewModel = songListViewModel
                        )
                    }
                }
            }
        }
        item {
            FindCard(
                title = R.string.app_top,
                true, true, true,
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    if (findviewModel.topcardData.isNotEmpty())
                        itemsIndexed(findviewModel.topcardData) { index, item ->
                            TopCard(
                                item.name,
                                item.id,
                                topsong = if (findviewModel.topsongData.size >= 3 * index + 3) findviewModel.topsongData.subList(
                                    3 * index,
                                    3 * index + 3
                                ) else listOf(),
                                findviewModel,
                                mediaPlayerViewModel,
                            )

                        }
                    else {
                        items(List(5) { 1 }) {
                            LoadingAnimation(
                                modifier = Modifier
                                    .width(350.dp)
                                    .height(380.dp)
                                    .padding(horizontal = 15.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )
                        }
                    }

                }

            }
        }
        item{
            Text(
                text = "这些歌单，你一定在找",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = modifier.padding(bottom = 5.dp)
            )
            if (songListViewModel.hotPlayList.isNotEmpty()) {
                Column{
                    RowThree2(albumArts = songListViewModel.hotPlayList.subList(13, 16),songListViewModel)
                    RowThree2(albumArts = songListViewModel.hotPlayList.subList(16, 19),songListViewModel)
                    RowThree2(albumArts = songListViewModel.hotPlayList.subList(7, 10),songListViewModel)
                    RowThree2(albumArts = songListViewModel.hotPlayList.subList(10, 13),songListViewModel)
                }
            }
        }
        item{
            Text(text = "暂无更多歌单")
        }
    }

}