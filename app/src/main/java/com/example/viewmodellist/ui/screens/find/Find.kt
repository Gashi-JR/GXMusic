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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.find.SongCover
import com.example.viewmodellist.ui.components.find.SonglistCover
import com.example.viewmodellist.ui.components.find.SonglistPreview
import com.example.viewmodellist.ui.components.find.TopAppBar
import com.example.viewmodellist.ui.components.find.TopCard
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.cardGradient
import com.example.viewmodellist.ui.theme.findcardGradient
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class, ExperimentalFoundationApi::class)
@Composable
fun Find(findviewModel: FindviewModel, modifier: Modifier = Modifier) {


    var isFixed = rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        findviewModel.fetchBannerData()
        isFixed.value = true
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

                    TopAppBar()

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
                R.string.find_recommendsonglist,
                modifier = Modifier.background(findcardGradient)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ) {
                    items(findviewModel.songItems) { item ->

                        SonglistCover(
                            imageUrl = item.imageUrl,
                            title = item.title,
                            playCount = item.playCount,
                            modifier = Modifier
                                .clickable(onClick = {})

                        )

                    }
                }
            }
        }
        item {
            FindCard(
                title = R.string.find_newsong,

                ) {
                LazyHorizontalGrid(
                    rows = GridCells.Fixed(3),
                    modifier = modifier.height(220.dp),
                    contentPadding = PaddingValues(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(60.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(findviewModel.songItems) { item ->
                        SongCover(
                            imageUrl = item.imageUrl,
                            title = item.title,
                            intro = item.playCount.toString(),
                            modifier = Modifier.clickable(onClick = {}),
                        )
                    }
                }
            }
        }
        item {
            FindCard(
                title = R.string.app_top,

                ) {
                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    items(findviewModel.songItems) { item ->
                        TopCard("云音乐说唱榜")
                    }
                }

            }
        }
        item {

            FindCard(
                R.string.find_mysub,
                //   modifier = Modifier.background(findcardGradient)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ) {
                    items(findviewModel.songItems) { item ->

                        SonglistPreview(
                            "https://p2.music.126.net/R2zySKjiX_hG8uFn1aCRcw==/109951165187830237.jpg",
                            "阿发发发疯阿发复旦复华",
                            "aaa",
                            listOf("aaa", "bbb")
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

        Find(FindviewModel())

    }
}


