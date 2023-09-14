package com.example.viewmodellist.ui.screens.top

import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.Tag
import com.example.viewmodellist.ui.components.find.AlbumArt
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.find.TopCard
import com.example.viewmodellist.ui.components.songlist.RowThree2
import com.example.viewmodellist.ui.components.top.Officialtopcard
import com.example.viewmodellist.ui.components.top.TopPageBar
import com.example.viewmodellist.ui.components.top.Topitem
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.utils.Datamodels
import com.example.viewmodellist.utils.formatter

@OptIn(ExperimentalLayoutApi::class, ExperimentalFoundationApi::class)
@Composable
fun Top(
    findviewModel: FindviewModel = FindviewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    topviewModel: TopviewModel = TopviewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    toSonglist: () -> Unit = {},
    toTop: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    LaunchedEffect(Unit) {
        topviewModel.fetchTopCardData()
        topviewModel.fetchMoreTopCardData()

    }
    val h = formatter.mainActivity?.getStatusBarHeight()!!

    LaunchedEffect(topviewModel.nowindex) {
        if (topviewModel.shouldUpdateIndex.value) {
            topviewModel.shouldUpdateIndex.value = false
            when (topviewModel.nowindex) {
                0 -> state.animateScrollToItem(0, 0)
                1 -> state.animateScrollToItem(6, 350)
                2 -> state.animateScrollToItem(8, 100)
            }
        }
    }

    LaunchedEffect(remember { derivedStateOf { state.firstVisibleItemIndex } }.value) {
        when (state.firstVisibleItemIndex) {
            in 0..5 -> topviewModel.nowindex = 0
            in 6..7 -> topviewModel.nowindex = 1
            else -> topviewModel.nowindex = 2
        }
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(horizontal = 15.dp),
        state = state
    ) {
        stickyHeader {
            Spacer(
                modifier = Modifier
                    .height((h + 18).dp)
                    .fillMaxWidth()
                    .background(Color(255, 251, 255))
            )
            TopPageBar(
                listOf("官方", "精选", "更多"),
                topviewModel
            )
        }

        item(key = "官方") {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cloudlogo),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier.size(22.dp)

                )
                Text(
                    text = "官方榜",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }

        if (findviewModel.topcardData.isNotEmpty())
            itemsIndexed(findviewModel.topcardData) { index, item ->
                Officialtopcard(
                    item.name,
                    item.updateFrequency,
                    item.id,
                    item.coverImgUrl,
                    topsong = if (findviewModel.topsongData.size >= 3 * index + 3) findviewModel.topsongData.subList(
                        3 * index,
                        3 * index + 3
                    ) else listOf(),
                    modifier = Modifier.clickable {
                        songListViewModel.detailId.value = item.id
                        songListViewModel.fetchSongLists()
                        songListViewModel.isShowDetail.value =
                            true
                        songListViewModel.des.value = item.description
                        songListViewModel.onBack.value = toTop
                        toSonglist()
                    },
                    findviewModel = findviewModel,
                    mediaPlayerViewModel = mediaPlayerViewModel,
                )

            }
        else {
            items(List(5) { 1 }) {
                LoadingAnimation(
                    modifier = Modifier
                        .width(480.dp)
                        .height(180.dp)
                        .padding(horizontal = 15.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }
        }



        item(key = "精选") {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.jingxuan1),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(22.dp)

                    )
                    Text(
                        text = "精选榜",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (topviewModel.topcardData.isNotEmpty())
                        topviewModel.topcardData.forEach { item ->
                            Box() {
                                Topitem(
                                    title = item.name,
                                    topid = item.id,
                                    findviewModel = findviewModel,
                                    topimg = item.coverImgUrl,
                                    updatetime = item.updateFrequency,
                                    onClick = {
                                        songListViewModel.detailId.value = item.id
                                        songListViewModel.fetchSongLists()
                                        songListViewModel.isShowDetail.value =
                                            true
                                        songListViewModel.des.value = item.description
                                        songListViewModel.onBack.value = toTop
                                        toSonglist()
                                    }
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.play),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                    modifier = Modifier
                                        .size(22.dp)
                                        .align(Alignment.TopEnd)

                                )
                            }
                        }
                    else {
                        (List(9) { 1 }).forEach { _ ->
                            LoadingAnimation(
                                modifier = Modifier
                                    .size(115.dp)
                                    .padding(bottom = 10.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )
                        }
                    }
                }
            }


        }
        item(key = "更多") {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.all2),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(22.dp)

                    )
                    Text(
                        text = "更多榜单",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }
                FlowRow(
                    maxItemsInEachRow = 3,
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (topviewModel.moretopcardData.isNotEmpty())
                        topviewModel.moretopcardData.forEach { item ->
                            Topitem(
                                title = item.name,
                                topid = item.id,
                                findviewModel = findviewModel,
                                topimg = item.coverImgUrl,
                                updatetime = item.updateFrequency,
                                onClick = {
                                    songListViewModel.detailId.value = item.id
                                    songListViewModel.fetchSongLists()
                                    songListViewModel.isShowDetail.value =
                                        true
                                    songListViewModel.des.value = item.description
                                    songListViewModel.onBack.value = toTop
                                    toSonglist()
                                }
                            )
                        }
                    else {
                        (List(9) { 1 }).forEach { _ ->
                            LoadingAnimation(
                                modifier = Modifier
                                    .size(115.dp)
                                    .padding(bottom = 10.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )
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


@Preview
@Composable
fun TopPreview() {
    ViewModelListTheme {
        Top()
    }
}