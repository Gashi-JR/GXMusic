package com.example.viewmodellist.ui.components.search


import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.songlist.TagItem
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import kotlinx.coroutines.launch


@Composable
fun SearchResult(
    searchviewModel: SearchviewModel,
    keyword: String,
    findviewModel: FindviewModel = FindviewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    songListViewModel: SongListViewModel,
    loginviewModel: LoginviewModel = LoginviewModel(),
    modifier: Modifier = Modifier
) {
    var index by rememberSaveable {
        mutableStateOf(0)
    }
    var page by rememberSaveable {
        mutableStateOf(0)
    }
    var sel by rememberSaveable {
        mutableStateOf(-1)
    }
    LaunchedEffect(keyword, index) {

        if (index == 0) {
            searchviewModel.clearresultSongData()
            searchviewModel.fetchResultSongData(keyword, 0)
        }
        if (index == 1) {
            searchviewModel.clearresultSonglistData()
            searchviewModel.fetchResultSonglistData(keyword, 0)
        }

    }
    LaunchedEffect(index) {
        page = 0
    }

    LaunchedEffect(page, index) {
        if (index == 0) {
            //    if (searchviewModel.resultSongData.isEmpty())
            searchviewModel.fetchResultSongData(keyword, page.toLong())
        }
        if (index == 1) {
            // if (searchviewModel.resultSonglistData.isEmpty())
            searchviewModel.fetchResultSonglistData(keyword, page.toLong())
        }
    }

    Column() {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TagItem(txt = "单曲", isSelected = index == 0) {
                index = 0
            }
            TagItem(txt = "歌单", isSelected = index == 1) {
                index = 1
            }
        }


        AnimatedVisibility(
            visible = index == 0,

            ) {
            val scope = rememberCoroutineScope() // 获取关联的协程作用域
            LazyColumn() {
                if (searchviewModel.resultSongData.isNotEmpty())
                    itemsIndexed(searchviewModel.resultSongData) { index, item ->
                        ResultSong(
                            index,
                            item,
                            index == sel,
                            modifier = Modifier.clickable {
                                sel = index
                                scope.launch {
                                    findviewModel.currentMusic.value.id = item.id
                                    findviewModel.currentMusic.value.name = item.name
                                    findviewModel.currentMusic.value.artist = item.artist
                                    findviewModel.fetchCurrentMusicUrl(item.id)
                                    findviewModel.fetchCurrentMusicPic(item.id)

                                    mediaPlayerViewModel.play(
                                        Repository().getCurrentMusicUrl(
                                            item.id
                                        )
                                    )

                                }
                            })

                        LaunchedEffect(searchviewModel.resultSongData.size) {
                            if (searchviewModel.resultSongData.size - index < 5) {
                                page++

                            }
                        }
                    }
                else {
                    items(List(10) { 1 }) {
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
        AnimatedVisibility(
            visible = index == 1,
        ) {
            LazyColumn(
                modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 15.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (searchviewModel.resultSonglistData.isNotEmpty())
                    itemsIndexed(searchviewModel.resultSonglistData) { index, item ->
                        ResultSonglist(item, onClick = {
                            songListViewModel.detailId.value = item.id
                            songListViewModel.fetchSongLists()
                            songListViewModel.isShowDetail.value = true
                            Log.d(TAG, "SearchResult: $songListViewModel.isShowDetail.value")
                        })
                        LaunchedEffect(searchviewModel.resultSonglistData.size) {
                            if (searchviewModel.resultSonglistData.size - index < 5) {
                                page++
                            }
                        }
                    }
                else {
                    items(List(10) { 1 }) {
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

}


@Preview(showBackground = true)
@Composable
fun SearchResultPreview() {
//    SearchResult(
//        searchviewModel = SearchviewModel(),
//        keyword = "ad",
//    )
}