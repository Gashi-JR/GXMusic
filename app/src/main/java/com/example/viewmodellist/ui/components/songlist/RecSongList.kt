package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
){

    //需要滚动
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ){
        //专属的推荐歌单 一共六个推荐（3*2）
        item {
            if (songListViewModel.songlistData.isNotEmpty()) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                    Text(
                        text = "Hi gnon2002，快来听听",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        AlbumArt(
                            imageUrl = songListViewModel.songlistData[0].picUrl,
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万"
                        )
                        AlbumArt(
                            imageUrl = songListViewModel.songlistData[1].picUrl,
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万"
                        )
                        AlbumArt(
                            imageUrl = songListViewModel.songlistData[2].picUrl,
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万"
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                        AlbumArt(
                            imageUrl = songListViewModel.songlistData[3].picUrl,
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万"
                        )
                        AlbumArt(
                            imageUrl = songListViewModel.songlistData[4].picUrl,
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万"
                        )
                        AlbumArt(
                            imageUrl = songListViewModel.songlistData[5].picUrl,
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万"
                        )

                    }
                }
            }
            else
                println("失败捏")
        }
        item {

            Text(text = "今日达人推荐",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)

            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)){
                items(6){
                    item->
                        AlbumArt("https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
                            title = "This is a title",
                            playCounts = 6346363636,
                            copywriter = "播放过万")
                }
            }
        }
        
        
        item { 
            Text(text = "日落与音乐相伴 睡个好觉 清晨和旋律相遇 午间律动",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)){
                items(6){
                        item->
                    AlbumArt("https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
                        title = "This is a title",
                        playCounts = 6346363636,
                        copywriter = "播放过万")
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

/*        item{
            Text(text = "这些歌单，你一定在找",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold)
            LazyVerticalGrid(columns = , content = )
        }*/
    }
}