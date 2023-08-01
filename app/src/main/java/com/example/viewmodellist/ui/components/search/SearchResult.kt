package com.example.viewmodellist.ui.components.search


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.screens.search.SearchviewModel


@SuppressLint("RememberReturnType")
@Composable
fun SearchResult(
    searchviewModel: SearchviewModel,
    keyword: String,
    modifier: Modifier = Modifier
) {
    var index by remember {
        mutableStateOf(0)
    }
    var page by rememberSaveable {
        mutableStateOf(0)
    }
    var loadedSongs = remember { searchviewModel.resultSongData }
    LaunchedEffect(keyword) {
        searchviewModel.fetchResultSongData(keyword, 0)
    }
// TODO: 解决搜索的问题

    LaunchedEffect(page) {
        searchviewModel.fetchResultSongData(keyword, page.toLong())
        loadedSongs = searchviewModel.resultSongData
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
            LazyColumn() {
                itemsIndexed(loadedSongs) { index, item ->
                    ResultSong(item)
                    LaunchedEffect(searchviewModel.resultSongData.size) {
                        if (searchviewModel.resultSongData.size - index < 5) {
                            page++
                        }
                    }
                }
            }

        }
        AnimatedVisibility(visible = index == 1) {
            Text(text = "b")
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