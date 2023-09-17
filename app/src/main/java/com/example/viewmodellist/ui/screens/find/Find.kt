package com.example.viewmodellist.ui.screens.find

import com.example.viewmodellist.ui.components.find.Banner
import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.find.SongCover
import com.example.viewmodellist.ui.components.find.SonglistCover
import com.example.viewmodellist.ui.components.find.TopAppBar
import com.example.viewmodellist.ui.components.find.TopCard
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.cardGradient
import com.example.viewmodellist.ui.theme.findcardGradient
import com.example.viewmodellist.utils.formatter
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun Find(
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    searchviewModel: SearchviewModel,
    loginviewModel: LoginviewModel = LoginviewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    toSonglist: () -> Unit = {},
    toTop: () -> Unit = {},
    toFind: () -> Unit = {},
    showSearch: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    LaunchedEffect(loginviewModel.result.value.cookie) {
        if (loginviewModel.result.value.cookie != "") {
            val pattern = Pattern.compile("MUSIC_U=([^;]+)")
            val matcher = pattern.matcher(loginviewModel.result.value.cookie)

            if (matcher.find()) {
                val keyValue = matcher.group(0).split("=")
                val key = keyValue[0]
                val value = keyValue[1]
                println("Key: $key")
                println("Value: $value")
            }
        }

    }

    val isFixed = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        if (findviewModel.topcardData.isEmpty())
            findviewModel.fetchTopCardData()
        if (findviewModel.bannerData.isEmpty())
            findviewModel.fetchBannerData()
        if (findviewModel.songlistData.isEmpty())
            findviewModel.fetchRecommendSonglistData()
        if (findviewModel.newsongData.isEmpty())
            findviewModel.fetchNewSongData()
        if (loginviewModel.User.value.nickname == "")
            loginviewModel.fetchUserInfo()
        if (searchviewModel.searchHotData.isEmpty())
            searchviewModel.fetchSearchHotData()
        if (songListViewModel.hotPlayList.isEmpty())
            songListViewModel.fetchHotPlaylist()
        isFixed.value = true
    }
    LaunchedEffect(findviewModel.topcardData) {
        findviewModel.fetchTopSongData(findviewModel.topcardData)
    }


    val h = formatter.mainActivity?.getStatusBarHeight()

    LazyColumn {
        item(key = 0) {
            Spacer(
                modifier = Modifier
                    .height(h!!.dp)
                    .fillMaxWidth()
                    .background(cardGradient)
            )
        }
        item(key = 1) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                modifier = Modifier.background(cardGradient)
            ) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = isFixed.value,
                    enter = slideInVertically(
                        // Enters by sliding down from offset -fullHeight to 0.
                        initialOffsetY = { fullHeight -> -fullHeight },
                        animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                    ),
                    exit = slideOutVertically(
                        // Exits by sliding up from offset 0 to -fullHeight.
                        targetOffsetY = { fullHeight -> -fullHeight },
                        animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
                    )
                ) {

                    TopAppBar(
                        searchviewModel = searchviewModel,
                        loginviewModel = loginviewModel,
                        onClick = showSearch
                    )

                }
                Banner(
                    list = findviewModel.bannerData,
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }


        }

        item(key = 2) {
            FindCard(
                R.string.find_recommendsonglist,
                showmore = true,
                showarrow = true,
                showline = true, onClick = toSonglist,
                modifier = Modifier.background(findcardGradient)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top,
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    modifier = Modifier.height(160.dp)

                ) {
                    if (findviewModel.songlistData.isNotEmpty())
                        items(
                            items = findviewModel.songlistData,
                            key = { item -> item.id }) { item ->

                            SonglistCover(
                                imageUrl = item.picUrl,
                                title = item.name,
                                playCount = if (item.playCount > 0) item.playCount else item.playCount,
                                copywriter = item.copywriter,
                                onClick = {
                                    songListViewModel.detailId.value = item.id
                                    songListViewModel.fetchSongLists()
                                    songListViewModel.isShowDetail.value =
                                        true
                                    songListViewModel.des.value = ""
                                    songListViewModel.onBack.value = toFind
                                    toSonglist()
                                },
                            )
                        }
                    else {
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
        }
        item(key = 3) {
            val scope = rememberCoroutineScope() // 获取关联的协程作用域
            FindCard(
                title = R.string.find_newsong,
                showmore = true, showarrow = true, showline = true,
            ) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    modifier = modifier.height(220.dp),
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(60.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (findviewModel.newsongData.isNotEmpty())
                        items(findviewModel.newsongData, key = { item ->
                            item.id
                        }) { item ->
                            SongCover(
                                picUrl = item.picUrl,
                                name = item.name,
                                artist = item.artist,
                                modifier = Modifier.clickable {

                                    scope.launch {
                                        findviewModel.currentMusic.value.id = item.id
                                        findviewModel.currentMusic.value.name = item.name
                                        findviewModel.currentMusic.value.picUrl = item.picUrl
                                        findviewModel.currentMusic.value.artist = item.artist
                                        findviewModel.fetchCurrentMusicUrl(item.id)
                                        mediaPlayerViewModel.play(
                                            Repository().getCurrentMusicUrl(
                                                item.id
                                            )
                                        )

                                    }

                                },
                            )
                        }
                    else {
                        items(List(9) { 1 }) {
                            LoadingAnimation(
                                modifier = Modifier
                                    .height(60.dp)
                                    .width(250.dp)
                                    .clip(MaterialTheme.shapes.small)
                            )
                        }
                    }

                }
            }
        }
        item(key = 4) {
            FindCard(
                title = R.string.app_top,
                showmore = true, showarrow = true, showline = true,
                onClick = toTop
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top,
                ) {
                    if (findviewModel.topcardData.isNotEmpty())
                        itemsIndexed(findviewModel.topcardData, key = { _, item ->
                            item.id
                        }) { index, item ->
                            TopCard(
                                item.name,
                                item.updateFrequency,
                                topsong = if (findviewModel.topsongData.size >= 3 * index + 3) findviewModel.topsongData.subList(
                                    3 * index,
                                    3 * index + 3
                                ) else listOf(),
                                findviewModel,
                                mediaPlayerViewModel,
                                onClick = {
                                    songListViewModel.detailId.value = item.id
                                    songListViewModel.fetchSongLists()
                                    songListViewModel.isShowDetail.value =
                                        true
                                    songListViewModel.des.value = item.description
                                    toSonglist()
                                }
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


        item(key = 5) {
            Spacer(modifier = Modifier.height(55.dp))
        }
    }


}

@Preview(showBackground = true)
@Composable
fun FindPreview() {
    ViewModelListTheme {

        Find(
            FindviewModel(),
            MediaPlayerViewModel(),
            SearchviewModel()
        )

    }
}


