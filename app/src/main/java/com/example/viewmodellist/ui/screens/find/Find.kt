package com.example.viewmodellist.ui.screens.find

import Banner
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.dokar.amlv.LyricsViewState
import com.dokar.amlv.rememberLyricsViewState
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.find.SongCover
import com.example.viewmodellist.ui.components.find.SonglistCover
import com.example.viewmodellist.ui.components.find.SonglistPreview
import com.example.viewmodellist.ui.components.find.TopAppBar
import com.example.viewmodellist.ui.components.find.TopCard
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.cardGradient
import com.example.viewmodellist.ui.theme.findcardGradient
import com.example.viewmodellist.utils.Datamodels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun Find(
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel,
    state: LyricsViewState,
    searchviewModel: SearchviewModel,
    showSearch:() -> Unit={},
    modifier: Modifier = Modifier
) {


    val isFixed = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
//        findviewModel.fetchTopCardData()
//        findviewModel.fetchBannerData()
//        findviewModel.fetchRecommendSonglistData()
        //findviewModel.fetchNewSongData()
        searchviewModel.fetchSearchHotData()
        isFixed.value = true
    }
    LaunchedEffect(findviewModel.topcardData) {
        // findviewModel.fetchTopSongData(findviewModel.topcardData)
    }




    LazyColumn() {
        item {
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
                        onClick = showSearch)

                }
                Banner(
                    list = findviewModel.bannerData,
                    modifier = Modifier
                        .padding(15.dp)
                        .clip(MaterialTheme.shapes.medium)
                )
            }


        }

        item {
            FindCard(
                R.string.find_recommendsonglist, true, true, true,
                modifier = Modifier.background(findcardGradient)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.Top,
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    modifier = Modifier.height(160.dp)

                ) {
                    if (findviewModel.songlistData.isNotEmpty())
                        items(findviewModel.songlistData) { item ->

                            SonglistCover(
                                imageUrl = item.picUrl,
                                title = item.name,
                                playCount = if (item.playcount > 0) item.playcount else item.playCount,
                                id = item.id,
                                copywriter = item.copywriter,
                                modifier = Modifier
                                    .clickable(onClick = {})

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
        item {
            val scope = rememberCoroutineScope() // 获取关联的协程作用域
            FindCard(
                title = R.string.find_newsong,
                true, true, true,
            ) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    modifier = modifier.height(220.dp),
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(60.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (findviewModel.newsongData.isNotEmpty())
                        items(findviewModel.newsongData) { item ->
                            SongCover(
                                picUrl = item.picUrl,
                                name = item.name,
                                id = item.id,
                                mvid = item.mvid,
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
        item {

            FindCard(
                R.string.find_mysub, true, true, true,
                //   modifier = Modifier.background(findcardGradient)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ) {
                    if (findviewModel.songlistData.isNotEmpty())
                        items(findviewModel.songlistData) { item ->

                            SonglistPreview(
                                "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
                                "阿发发发疯阿发复旦复华",
                                "aaa",
                                listOf("aaa", "bbb")
                            )

                        }
                    else
                        items(List(3) { 1 }) {
                            LoadingAnimation(
                                modifier = Modifier
                                    .width(350.dp)
                                    .height(630.dp)
                                    .padding(horizontal = 15.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )
                        }
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.find_nomore),
                    color = Color.Gray.copy(alpha = 0.5f)
                )
            }

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
            rememberLyricsViewState(lrcContent = "", mediaPlayerViewModel = MediaPlayerViewModel()),
            SearchviewModel()
        )

    }
}


