package com.example.viewmodellist.ui.components.search


import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import kotlinx.coroutines.launch


@Composable
fun SearchResult(
    searchviewModel: SearchviewModel,
    keyword: String,
    findviewModel: FindviewModel = FindviewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    modifier: Modifier = Modifier
) {
    var index by remember {
        mutableStateOf(0)
    }
    var page by rememberSaveable {
        mutableStateOf(0)
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
            searchviewModel.fetchResultSongData(keyword, page.toLong())
        }
        if (index == 1) {
            searchviewModel.fetchResultSonglistData(keyword, page.toLong())
        }
    }

    Column() {
        TabRow(selectedTabIndex = index, indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[index])
                    .clip(MaterialTheme.shapes.large),
                color = Color.Red, // Customize the indicator color
                height = 1.dp // Customize the indicator height
            )
        }) {
            Tab(
                selected = true, onClick = { index = 0 },
                text = {
                    Text(text = "单曲", color = Color.Black)
                },
            )
            Tab(selected = true, onClick = { index = 1 },
                text = {
                    Text(text = "歌单", color = Color.Black)
                })
        }

        AnimatedVisibility(visible = index == 0) {
            val scope = rememberCoroutineScope() // 获取关联的协程作用域
            LazyColumn() {
                itemsIndexed(searchviewModel.resultSongData) { index, item ->
                    ResultSong(item, modifier = Modifier.clickable {
                        scope.launch {
                            findviewModel.currentMusic.value.id = item.id
                            findviewModel.currentMusic.value.name = item.name
                            findviewModel.currentMusic.value.artist = item.artist
                            findviewModel.fetchCurrentMusicUrl(item.id)
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
            }

        }
        AnimatedVisibility(visible = index == 1) {

            LazyColumn(
                modifier = Modifier.padding(start = 15.dp, top = 8.dp, end = 15.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(searchviewModel.resultSonglistData) { index, item ->
                    ResultSonglist(item)
                    LaunchedEffect(searchviewModel.resultSonglistData.size) {
                        if (searchviewModel.resultSonglistData.size - index < 5) {
                            page++
                        }
                    }
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun SearchResultPreview() {
    SearchResult(
        searchviewModel = SearchviewModel(),
        keyword = "ad",
    )
}