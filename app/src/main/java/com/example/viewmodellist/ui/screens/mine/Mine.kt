package com.example.viewmodellist.ui.screens.mine


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.mine.MySonglists
import com.example.viewmodellist.ui.components.search.ResultSonglist
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.mylikemusicGradient
import com.example.viewmodellist.ui.theme.recentmusicGradient
import com.example.viewmodellist.ui.theme.vinylTimeMachineGradient
import com.example.viewmodellist.utils.Datamodels
import com.example.viewmodellist.utils.formatter

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Mine(
    loginviewModel: LoginviewModel = LoginviewModel(),
    mineviewModel: MineviewModel = MineviewModel(),
    onLogin: () -> Unit = {},
) {
    var isDetail by remember {
        mutableStateOf(false)
    }
    var isEdit by remember {
        mutableStateOf(false)
    }

    var editname by remember {
        mutableStateOf(false)
    }
    var editgender by remember {
        mutableStateOf(false)
    }
    var editarea by remember {
        mutableStateOf(false)
    }
    var editdate by remember {
        mutableStateOf(false)
    }
    var editsignature by remember {
        mutableStateOf(false)
    }
    var nickname by remember {
        mutableStateOf(loginviewModel.User.value.nickname)
    }
    var selectIndex by remember {
        mutableStateOf(loginviewModel.User.value.gender)
    }
    var areaIndex by remember {
        mutableStateOf(loginviewModel.User.value.province.toInt())
    }
    val signature = remember {
        mutableStateOf(TextFieldValue(loginviewModel.User.value.signature))
    }
    val maxlen = remember {
        mutableStateOf(100)
    }
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = loginviewModel.User.value.birthday)
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    LaunchedEffect(Unit) {
        mineviewModel.fetchResultSonglistData(loginviewModel.uid.value)
    }






    AnimatedVisibility(
        visible = !isDetail && !isEdit, enter = slideInVertically(
            initialOffsetY = { -it },
        ), exit = slideOutVertically(targetOffsetY = { it })
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(245, 245, 245)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box() {
                    Image(
                        painter = rememberAsyncImagePainter(loginviewModel.User.value.avatarUrl),
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
                                    text = loginviewModel.User.value.nickname,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                                if (selectIndex == 1) Icon(
                                    painter = painterResource(id = R.drawable.nan),
                                    contentDescription = "man",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(20.dp),
                                )
                                if (selectIndex == 2) Icon(
                                    painter = painterResource(id = R.drawable.xingbienv),
                                    contentDescription = "man",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(20.dp),
                                )
                            }

                            Text(
                                text = "${loginviewModel.User.value.follows}关注 | ${loginviewModel.User.value.followeds}粉丝 | Lv.${loginviewModel.User.value.level}",
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
                                ) {
                                    Text(
                                        text = "IP:未知",
                                        textAlign = TextAlign.Center,
                                        fontSize = 11.sp
                                    )
                                }
                                Card(
                                    border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    modifier = Modifier.width(65.dp),
                                    elevation = 0.dp
                                ) {
                                    Text(
                                        text =
                                        formatter.convertTimestampToDateString(
                                            datePickerState.selectedDateMillis!!
                                        ),
                                        textAlign = TextAlign.Center,
                                        fontSize = 11.sp
                                    )
                                }
                                Card(
                                    border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    modifier = Modifier.width(55.dp),
                                    elevation = 0.dp
                                ) {
                                    Text(
                                        text = formatter.getRegionName(areaIndex.toString()),
                                        textAlign = TextAlign.Center,
                                        fontSize = 11.sp,
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 1
                                    )

                                }
                                Card(
                                    border = BorderStroke(0.05.dp, Color.Gray.copy(0.3f)),
                                    shape = MaterialTheme.shapes.extraSmall,
                                    modifier = Modifier.width(65.dp),
                                    elevation = 0.dp
                                ) {
                                    Text(
                                        text = "村龄${
                                            ((System.currentTimeMillis() - loginviewModel.User.value.createTime) / 86400000) + 1
                                        }天", textAlign = TextAlign.Center, fontSize = 11.sp
                                    )
                                }
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                            ) {
                                Button(
                                    onClick = { isEdit = true },
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
                                Button(
                                    onClick = {
                                        mineviewModel.logout()
                                        onLogin()
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                                    shape = MaterialTheme.shapes.large,
                                    elevation = ButtonDefaults.elevation(0.dp),
                                    border = BorderStroke(0.1.dp, Color.Gray.copy(0.4f))
                                ) {
                                    Text(
                                        text = "退出登录",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 13.sp
                                    )
                                }
                            }
                        }

                    }

                    Image(
                        painter = rememberAsyncImagePainter(loginviewModel.User.value.avatarUrl),
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
                    modifier = Modifier.width(370.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 0.1.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(
                            top = 15.dp, start = 15.dp, end = 15.dp, bottom = 8.dp
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
                            androidx.compose.material3.Card(colors = CardDefaults.cardColors(
                                containerColor = Color(0, 0, 0, 0),
                            ), modifier = Modifier
                                .size(109.dp)

                                .border(
                                    0.8.dp, Color(247, 241, 241), MaterialTheme.shapes.medium
                                )
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
                                        Text(
                                            text = "我的喜欢", color = Color.Gray, fontSize = 12.sp
                                        )

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
                            androidx.compose.material3.Card(colors = CardDefaults.cardColors(
                                containerColor = Color(0, 0, 0, 0),
                            ), modifier = Modifier
                                .size(109.dp)

                                .border(
                                    0.8.dp, Color(249, 245, 235), MaterialTheme.shapes.medium
                                )
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
                                        Text(
                                            text = "最近播放", color = Color.Gray, fontSize = 12.sp
                                        )

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
                            androidx.compose.material3.Card(colors = CardDefaults.cardColors(
                                containerColor = Color(0, 0, 0, 0),
                            ), modifier = Modifier
                                .size(109.dp)

                                .border(
                                    0.8.dp, Color(240, 244, 247), MaterialTheme.shapes.medium
                                )
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
                                        Text(
                                            text = "本周关键词",
                                            color = Color.Gray,
                                            fontSize = 12.sp
                                        )

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
                    modifier = Modifier.width(370.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 0.1.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(
                                top = 15.dp, start = 15.dp, end = 15.dp, bottom = 8.dp
                            )
                            .height(300.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Text(
                                text = "收藏的歌单", fontWeight = FontWeight.Bold, fontSize = 19.sp
                            )

                            Text(
                                text = "(${mineviewModel.mySonglistData.size})",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        LazyHorizontalGrid(rows = GridCells.Fixed(4)) {
                            items(mineviewModel.mySonglistData) { item ->
                                MySonglists(
                                    songlist = Datamodels.MySonglist(
                                        item.id,
                                        item.trackCount,
                                        item.playCount,
                                        item.coverImgUrl,
                                        item.name,
                                        item.creater,
                                        item.tags
                                    ),
                                    modifier = Modifier.clickable { }
                                )


                            }


                        }

                    }

                }
            }


            item {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier.width(370.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = 0.1.dp
                ) {
                    Column(

                        modifier = Modifier.height(230.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(15.dp),
                            modifier = Modifier.padding(
                                top = 15.dp, start = 15.dp, end = 15.dp, bottom = 8.dp
                            )
                        ) {
                            Text(
                                text = "基本信息", fontWeight = FontWeight.Bold, fontSize = 19.sp
                            )
                            Text(
                                text = "村龄: ${((System.currentTimeMillis() - loginviewModel.User.value.createTime) / 86400000) + 1}天(${
                                    formatter.convertTimestampToDateString(
                                        loginviewModel.User.value.createTime
                                    ).split("-")[0]
                                }年${
                                    formatter.convertTimestampToDateString(loginviewModel.User.value.createTime)
                                        .split("-")[1]
                                }月注册)", color = Color.Gray, fontSize = 14.sp
                            )
                            Text(
                                text = "性别: ${if (selectIndex == 0) "未知" else if (selectIndex == 1) "男" else "女"}",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "年龄: ${
                                    formatter.convertTimestampToDateString(
                                        datePickerState.selectedDateMillis!!
                                    ).split("-")[0][2]
                                }0后 ", color = Color.Gray, fontSize = 14.sp
                            )
                            Text(
                                text = "个人简介: ${signature.value.text}",
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
                                .clickable { isDetail = true },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "查看全部", color = Color.Gray.copy(0.7f), fontSize = 13.sp
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


    AnimatedVisibility(
        visible = isDetail, enter = slideInVertically(
            initialOffsetY = { it },
        ), exit = slideOutVertically(targetOffsetY = { -it })
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp), modifier = Modifier
                .padding(
                    top = 15.dp, start = 15.dp, end = 15.dp, bottom = 8.dp
                )
                .fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(onClick = { isDetail = false }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = "search",
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = "基本信息", fontSize = 19.sp
                )
            }
            Text(
                text = "个人信息", fontWeight = FontWeight.Bold, fontSize = 15.sp
            )
            Text(
                text = "村龄: ${((System.currentTimeMillis() - loginviewModel.User.value.createTime) / 86400000) + 1}天(${
                    formatter.convertTimestampToDateString(
                        loginviewModel.User.value.createTime
                    ).split("-")[0]
                }年${
                    formatter.convertTimestampToDateString(loginviewModel.User.value.createTime)
                        .split("-")[1]
                }月注册)", color = Color.Gray, fontSize = 14.sp
            )
            Text(
                text = "年龄: ${
                    formatter.convertTimestampToDateString(
                        datePickerState.selectedDateMillis!!
                    ).split("-")[0][2]
                }0后 ", color = Color.Gray, fontSize = 14.sp
            )
            Text(
                text = "性别: ${if (selectIndex == 0) "未知" else if (selectIndex == 1) "男" else "女"}",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Text(
                text = "地区: ${formatter.getRegionName(areaIndex.toString())}",
                color = Color.Gray,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "个人介绍", fontWeight = FontWeight.Bold, fontSize = 15.sp
            )
            Text(
                text = signature.value.text,
                color = Color.Gray,
                fontSize = 14.sp,
                maxLines = 99,
                overflow = TextOverflow.Ellipsis
            )
        }

    }


    AnimatedVisibility(
        visible = isEdit, enter = slideInVertically(
            initialOffsetY = { it },
        ), exit = slideOutVertically(targetOffsetY = { -it })
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 15.dp, start = 15.dp, end = 15.dp, bottom = 8.dp
                )

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                IconButton(onClick = {
                    isEdit = false
                    mineviewModel.modifyUserInfo(
                        datePickerState.selectedDateMillis!!,
                        areaIndex.toLong(),
                        signature.value.text,
                        selectIndex
                    )
                    mineviewModel.modifyUsername(nickname)
                    loginviewModel.fetchUserInfo()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = "我的资料", fontSize = 19.sp
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 0.1.dp
            ) {
                Column(verticalArrangement = Arrangement.SpaceAround) {
                    Row(modifier = Modifier
                        .clickable { }
                        .fillMaxWidth()
                        .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "头像", fontSize = 17.sp
                        )
                        Image(
                            painter = rememberAsyncImagePainter(loginviewModel.User.value.avatarUrl),
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(33.dp)
                        )
                    }
                    Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                    Row(modifier = Modifier
                        .clickable { editname = true }
                        .fillMaxWidth()
                        .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "昵称", fontSize = 17.sp
                        )
                        Text(
                            text = nickname,
                            fontSize = 15.sp,
                            color = Color.Gray.copy(0.7f)
                        )
                    }
                    Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                    Row(modifier = Modifier
                        .clickable { editgender = true }
                        .fillMaxWidth()
                        .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "性别", fontSize = 17.sp
                        )
                        Text(
                            text = " ${if (selectIndex == 0) "未知" else if (selectIndex == 1) "男" else "女"}",
                            fontSize = 15.sp,
                            color = Color.Gray.copy(0.7f)
                        )
                    }

                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = MaterialTheme.shapes.medium,
                elevation = 0.1.dp
            ) {
                Column(verticalArrangement = Arrangement.SpaceAround) {
                    Row(modifier = Modifier
                        .clickable { editdate = true }
                        .fillMaxWidth()
                        .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "生日", fontSize = 17.sp
                        )
                        Text(
                            text = formatter.convertTimestampToDateString(datePickerState.selectedDateMillis!!),
                            textAlign = TextAlign.Center,
                            fontSize = 15.sp,
                            color = Color.Gray.copy(0.7f)
                        )
                    }
                    Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                    Row(modifier = Modifier
                        .clickable { editarea = true }
                        .fillMaxWidth()
                        .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "地区", fontSize = 17.sp
                        )
                        Text(
                            text = formatter.getRegionName(areaIndex.toString()),
                            fontSize = 15.sp,
                            color = Color.Gray.copy(0.7f)
                        )
                    }
                    Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                    Row(modifier = Modifier
                        .clickable { editsignature = true }
                        .fillMaxWidth()
                        .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "简介", fontSize = 17.sp
                        )
                        Text(
                            text = signature.value.text,
                            fontSize = 15.sp,
                            color = Color.Gray.copy(0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(150.dp)
                        )
                    }

                }
            }

        }

    }


    AnimatedVisibility(visible = editname) {
        AlertDialog(onDismissRequest = { /*TODO*/ }, buttons = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Button(
                    onClick = {
                        nickname = loginviewModel.User.value.nickname
                        editname = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "取消")
                }
                Button(
                    onClick = {
                        editname = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp),
                    enabled = !(mineviewModel.duplicated && nickname != loginviewModel.User.value.nickname)
                ) {
                    Text(text = "保存")
                }
            }
        }, title = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "昵称:")
                Text(
                    text = "每天仅可修改两次",
                    fontSize = 13.sp,
                    color = Color.Gray.copy(0.7f)
                )
                AnimatedVisibility(
                    visible = mineviewModel.duplicated && nickname != loginviewModel.User.value.nickname
                ) {
                    Text(text = "昵称已存在", color = Color.Red.copy(0.7f))
                }
            }

        }, text = {
            val containerColor = Color.Gray.copy(0f)

            TextField(
                value = nickname,
                onValueChange = {
                    nickname = it
                    mineviewModel.checkNickname(it)
                },
                maxLines = 1,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    disabledContainerColor = containerColor,
                    cursorColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray,
                    unfocusedIndicatorColor = Color.Gray,
                )

            )
        }, modifier = Modifier.padding(15.dp), shape = MaterialTheme.shapes.large
        )
    }



    AnimatedVisibility(visible = editgender) {
        AlertDialog(onDismissRequest = { /*TODO*/ }, buttons = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Button(
                    onClick = {
                        selectIndex = loginviewModel.User.value.gender
                        editgender = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "取消")
                }
                Button(
                    onClick = { editgender = false },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "保存")
                }
            }
        }, title = {
            Text(text = "性别:")
        }, text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectIndex = 1 },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "男")
                    if (selectIndex == 1) Icon(
                        painter = painterResource(id = R.drawable.duigou),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red.copy(0.4f)
                    )
                }

                Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { selectIndex = 2 },
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "女")
                    if (selectIndex == 2) Icon(
                        painter = painterResource(id = R.drawable.duigou),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Red.copy(0.4f)
                    )
                }


            }

        }, modifier = Modifier.padding(15.dp), shape = MaterialTheme.shapes.large
        )
    }



    AnimatedVisibility(visible = editdate) {

        DatePickerDialog(onDismissRequest = {
            editdate = false
        }, colors = DatePickerDefaults.colors(
            containerColor = Color.White,
        ), shape = MaterialTheme.shapes.medium, confirmButton = {
            TextButton(
                onClick = {
                    editdate = false
                    println("选中时间戳为： ${datePickerState.selectedDateMillis}")
                }, enabled = confirmEnabled.value
            ) {
                Text("确定")
            }
        }, dismissButton = {
            TextButton(onClick = {
                datePickerState.setSelection(loginviewModel.User.value.birthday)
                editdate = false
            }) {
                Text("取消")
            }
        }) {
            DatePicker(state = datePickerState)

        }
    }



    AnimatedVisibility(visible = editarea) {
        AlertDialog(onDismissRequest = { /*TODO*/ }, buttons = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Button(
                    onClick = {
                        areaIndex = loginviewModel.User.value.province.toInt()
                        editarea = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "取消")
                }
                Button(
                    onClick = {

                        editarea = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "保存")
                }
            }
        }, text = {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                items(
                    listOf(
                        "北京市",
                        "天津市",
                        "河北省",
                        "山西省",
                        "内蒙古自治区",
                        "辽宁省",
                        "吉林省",
                        "黑龙江省",
                        "上海市",
                        "江苏省",
                        "浙江省",
                        "安徽省",
                        "福建省",
                        "江西省",
                        "山东省",
                        "河南省",
                        "湖北省",
                        "湖南省",
                        "广东省",
                        "广西壮族自治区",
                        "海南省",
                        "重庆市",
                        "四川省",
                        "贵州省",
                        "云南省",
                        "西藏自治区",
                        "陕西省",
                        "甘肃省",
                        "青海省",
                        "宁夏回族自治区",
                        "新疆维吾尔自治区",
                        "台湾省",
                        "香港特别行政区",
                        "澳门特别行政区"
                    )
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    areaIndex = formatter
                                        .getCodeByRegionName(it)
                                        ?.toInt()!!
                                }, horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = it)
                            if (areaIndex == formatter.getCodeByRegionName(it)?.toInt()!!) Icon(
                                painter = painterResource(id = R.drawable.duigou),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                                tint = Color.Red.copy(0.4f)
                            )
                        }

                        Divider(thickness = 0.5.dp, color = Color.Gray.copy(0.2f))
                    }

                }


            }

        }, modifier = Modifier.padding(15.dp), shape = MaterialTheme.shapes.large
        )
    }



    AnimatedVisibility(visible = editsignature) {
        AlertDialog(onDismissRequest = { /*TODO*/ }, buttons = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
            ) {
                Button(
                    onClick = {
                        signature.value = TextFieldValue(loginviewModel.User.value.signature)
                        editsignature = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "取消")
                }
                Button(
                    onClick = {


                        editsignature = false
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    elevation = ButtonDefaults.elevation(0.5.dp)
                ) {
                    Text(text = "保存")
                }
            }
        }, title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "简介: ")
                Text(
                    text = "还剩${maxlen.value - signature.value.text.length}字",
                    color = Color.Gray.copy(0.5f),
                    fontSize = 14.sp
                )
            }

        }, text = {
            val containerColor = Color.Gray.copy(0.05f)
            TextField(value = signature.value, onValueChange = {
                if (it.text.length <= maxlen.value) {
                    signature.value = it
                }
            }, placeholder = {
                Text(text = signature.value.text)
            }, colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = containerColor,
                cursorColor = Color.Gray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ), minLines = 9

            )
        }, modifier = Modifier.padding(15.dp), shape = MaterialTheme.shapes.large
        )
    }


}


@Preview
@Composable
fun MinePreview() {
    ViewModelListTheme {
        Mine()
    }
}