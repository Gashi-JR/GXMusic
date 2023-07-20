package com.example.viewmodellist.ui.screens.find

import Banner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material3.BottomAppBarDefaults.windowInsets
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.OptionButton
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.ui.components.FindCard
import com.example.viewmodellist.ui.components.SonglistCover


@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun Find(findviewModel: FindviewModel, modifier: Modifier = Modifier) {

    val cardGradient = Brush.horizontalGradient(
        colors = listOf(Color(217, 220, 253), Color(244, 216, 239)), // 渐变色列表
        startX = 0.5f,
        endX = with(LocalDensity.current) { 350.dp.toPx() } // 渐变色高度
    )
    val borderGradient = Brush.horizontalGradient(
        colors = listOf(Color.Blue.copy(alpha = 0.15f), Color.Red.copy(alpha = 0.15f)), // 渐变色列表
        startX = 0.5f,
        endX = with(LocalDensity.current) { 350.dp.toPx() } // 渐变色高度
    )
    val findcardGradient = Brush.verticalGradient(
        colors = listOf(Color(244, 216, 239), Color.White), // 渐变色列表
        startY = 0.5f,
        endY = with(LocalDensity.current) { 200.dp.toPx() } // 渐变色高度
    )

    LaunchedEffect(Unit) {
        findviewModel.fetchBannerData()
    }


    LazyColumn() {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                modifier = Modifier.background(cardGradient)
            ) {
                Row() {

                    OptionButton(
                        modifier = Modifier
                            .height(50.dp)
                            .padding(top = 10.dp)
                    )
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                        modifier = Modifier
                            .weight(1F)
                            .height(50.dp)
                            .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                            .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                            .clip(MaterialTheme.shapes.medium),

                        ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                                    .fillMaxHeight()
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_search_24),
                                    contentDescription = "search"
                                )

                                Text(text = "大家都在搜 ", color = Color.Gray, fontSize = 14.sp)
                            }

                            Icon(
                                painter = painterResource(id = R.drawable.baseline_clear_24),
                                contentDescription = "clear"
                            )
                        }


                    }
                    Spacer(modifier = Modifier.width(30.dp))
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
                title = R.string.find_recommendsonglist,
                modifier = Modifier.background(findcardGradient)
            )
        }
        item {
            FindCard(
                title = R.string.find_recommendsonglist,

                )
        }
        item {
            FindCard(
                title = R.string.find_recommendsonglist,

                )
        }
        item {
            FindCard(
                title = R.string.find_recommendsonglist,
            )
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


