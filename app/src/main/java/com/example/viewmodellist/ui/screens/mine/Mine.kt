package com.example.viewmodellist.ui.screens.mine

import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Start
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.Tag
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.search.ResultSonglist
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.borderGradient
import com.example.viewmodellist.ui.theme.findcardGradient
import com.example.viewmodellist.ui.theme.mylikemusicGradient
import com.example.viewmodellist.ui.theme.recentmusicGradient
import com.example.viewmodellist.ui.theme.vinylTimeMachineGradient
import com.example.viewmodellist.utils.Datamodels

@Composable
fun Mine() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(245, 245, 245)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Box() {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Card(
                    modifier = Modifier
                        .width(370.dp)
                        .height(200.dp)
                        .align(BiasAlignment(0f, 18f)),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 1.dp
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(top = 40.dp)
                            .fillMaxWidth()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "啊啊啊啊啊",
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.nan),
                                contentDescription = "man",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp),
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.xingbienv),
                                contentDescription = "man",
                                tint = Color.Unspecified,
                                modifier = Modifier.size(20.dp),
                            )
                        }

                        Text(
                            text = "0关注 | 0粉丝 | Lv.0",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier
                        ) {
                            Card(
                                border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                shape = MaterialTheme.shapes.extraSmall,
                                modifier = Modifier.width(60.dp),
                                elevation = 0.dp
                            )
                            {
                                Text(
                                    text = "IP:湖北",
                                    textAlign = TextAlign.Center,
                                    fontSize = 13.sp
                                )
                            }
                            Card(
                                border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                shape = MaterialTheme.shapes.extraSmall,
                                modifier = Modifier.width(65.dp),
                                elevation = 0.dp
                            )
                            {
                                Text(
                                    text = "1970-5-1", textAlign = TextAlign.Center,
                                    fontSize = 13.sp
                                )
                            }
                            Card(
                                border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                shape = MaterialTheme.shapes.extraSmall,
                                modifier = Modifier.width(55.dp),
                                elevation = 0.dp
                            )
                            {
                                Text(
                                    text = "湖北省", textAlign = TextAlign.Center,
                                    fontSize = 13.sp
                                )

                            }
                            Card(
                                border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                shape = MaterialTheme.shapes.extraSmall,
                                modifier = Modifier.width(65.dp),
                                elevation = 0.dp
                            )
                            {
                                Text(
                                    text = "村龄55天", textAlign = TextAlign.Center,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                        ) {
                            Button(
                                onClick = { /*TODO*/ },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                shape = MaterialTheme.shapes.large,
                                elevation = ButtonDefaults.elevation(0.dp),
                                border = BorderStroke(0.1.dp, Color.Gray.copy(0.4f))
                            ) {
                                Text(
                                    text = "编辑资料",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }

                }

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_background),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .zIndex(1f)
                        .align(BiasAlignment(0f, 1f))

                )

            }
        }
        item {
            Spacer(modifier = Modifier.height(190.dp))
            Card(
                modifier = Modifier
                    .width(370.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 0.1.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(
                        top = 15.dp,
                        start = 15.dp,
                        end = 15.dp,
                        bottom = 8.dp
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.mine_musicstyle),
                        fontWeight = FontWeight.Bold,
                        fontSize = 19.sp

                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        androidx.compose.material3.Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0, 0, 0, 0),
                            ),
                            modifier = Modifier
                                .size(109.dp)

                                .border(0.8.dp, Color(247, 241, 241), MaterialTheme.shapes.medium)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { }

                        ) {
                            Column(
                                modifier = Modifier
                                    .background(mylikemusicGradient)
                                    .padding(8.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(text = "我的喜欢", color = Color.Gray, fontSize = 12.sp)

                                    Text(text = "0首", fontSize = 15.sp)
                                }

                                Row() {
                                    Icon(
                                        painter = painterResource(id = R.drawable.aixin_shixin),
                                        tint = Color.Gray.copy(0.7f),
                                        contentDescription = null,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "喜欢的音乐",
                                        color = Color.Gray.copy(0.7f),
                                        fontSize = 11.sp
                                    )
                                }

                            }
                        }
                        androidx.compose.material3.Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0, 0, 0, 0),
                            ),
                            modifier = Modifier
                                .size(109.dp)

                                .border(0.8.dp, Color(249, 245, 235), MaterialTheme.shapes.medium)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { }

                        ) {
                            Column(
                                modifier = Modifier
                                    .background(recentmusicGradient)
                                    .padding(8.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(text = "最近播放", color = Color.Gray, fontSize = 12.sp)

                                    Text(text = "0首", fontSize = 15.sp)
                                }

                                Row() {
                                    Icon(
                                        painter = painterResource(id = R.drawable.pcbofangye_paihangbang),
                                        tint = Color.Gray.copy(0.7f),
                                        contentDescription = null,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "最近听歌",
                                        color = Color.Gray.copy(0.7f),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                        androidx.compose.material3.Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0, 0, 0, 0),
                            ),
                            modifier = Modifier
                                .size(109.dp)

                                .border(0.8.dp, Color(240, 244, 247), MaterialTheme.shapes.medium)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable { }

                        ) {
                            Column(
                                modifier = Modifier
                                    .background(vinylTimeMachineGradient)
                                    .padding(8.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Text(text = "本周关键词", color = Color.Gray, fontSize = 12.sp)

                                    Text(text = "属于你的音乐档案", fontSize = 14.sp)
                                }

                                Row() {
                                    Icon(
                                        painter = painterResource(id = R.drawable.hourglass_fill),
                                        tint = Color.Gray.copy(0.7f),
                                        contentDescription = null,
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Text(
                                        text = "黑胶时光机",
                                        color = Color.Gray.copy(0.7f),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }

            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .width(370.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 0.1.dp
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .padding(
                            top = 15.dp,
                            start = 15.dp,
                            end = 15.dp,
                            bottom = 8.dp
                        )
                        .height(300.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(
                            text = "收藏的歌单",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )

                        Text(
                            text = "(2)",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                    LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
                        listOf(1, 1, 1, 1, 1, 1, 1, 1, 1, 1).forEach {
                            item {
                                ResultSonglist(
                                    songlist = Datamodels.ResultSonglist(
                                        1,
                                        1,
                                        1,
                                        "http://p1.music.126.net/2zSNIqTcpHL2jIvU6hG0EA==/109951162868128395.jpg",
                                        "asfafaf",
                                        "afafafa",
                                        null
                                    )
                                )
                            }
                        }


                    }

                }

            }
        }
        item {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .width(370.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 0.1.dp
            ) {
                Column(

                    modifier = Modifier
                        .height(230.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(15.dp), modifier = Modifier
                            .padding(
                                top = 15.dp,
                                start = 15.dp,
                                end = 15.dp,
                                bottom = 8.dp
                            )
                    ) {
                        Text(
                            text = "基本信息",
                            fontWeight = FontWeight.Bold,
                            fontSize = 19.sp
                        )
                        Text(
                            text = "村龄: 116天(2023年04月注册)",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "性别: 女",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "年龄: 70后 ",
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "个人简介: 卡卡卡卡卡卡卡卡",
                            color = Color.Gray,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable { },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "查看全部", color = Color.Gray.copy(0.7f),
                            fontSize = 13.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(15.dp)
                        )
                    }

                }
            }
        }
        item { Spacer(modifier = Modifier.height(65.dp)) }
    }
}


@Preview
@Composable
fun MinePreview() {
    ViewModelListTheme {
        Mine()
    }
}