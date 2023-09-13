package com.example.viewmodellist.ui.screens.top

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.viewmodellist.ui.components.top.Topitem
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.utils.Datamodels
import com.example.viewmodellist.utils.formatter

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Top(
    findviewModel: FindviewModel = FindviewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    topviewModel: TopviewModel = TopviewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    modifier: Modifier = Modifier
) {

    val showrec = remember {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        topviewModel.fetchTopCardData()
        topviewModel.fetchMoreTopCardData()
    }
    LaunchedEffect(Unit) {

    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = Modifier.padding(horizontal = 15.dp)

    ) {
        item {
            Spacer(
                modifier = Modifier
                    .height(formatter.mainActivity?.getStatusBarHeight()!!.dp)
                    .fillMaxWidth()
            )
        }



        item {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
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
                    modifier = Modifier.clickable { },
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



        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                    topviewModel.topcardData.forEach { item ->
                        Topitem(
                            title = item.name,
                            topid = item.id,
                            findviewModel = findviewModel,
                            topimg = item.coverImgUrl,
                            updatetime = item.updateFrequency
                        )
                    }
                }
            }


        }
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
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
                    topviewModel.moretopcardData.forEach { item ->
                        Topitem(
                            title = item.name,
                            topid = item.id,
                            findviewModel = findviewModel,
                            topimg = item.coverImgUrl,
                            updatetime = item.updateFrequency
                        )
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