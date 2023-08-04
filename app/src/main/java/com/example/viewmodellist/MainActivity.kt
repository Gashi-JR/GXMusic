package com.example.viewmodellist


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.dokar.amlv.rememberLyricsViewState
import com.example.viewmodellist.ui.components.BottomBar
import com.example.viewmodellist.ui.components.PlayButton
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.Find
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.ui.screens.login.Login
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.lyricsview.LyricPage
import com.example.viewmodellist.ui.screens.mine.Mine
import com.example.viewmodellist.ui.screens.search.Search
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.screens.songlist.SongList
import com.example.viewmodellist.ui.screens.top.Top
import com.example.viewmodellist.ui.theme.ViewModelListTheme


class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelListTheme {
                // A surface container using the 'background' color from the theme
                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {
                    Myapp()
                }
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Myapp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    var backPressedCount by remember {  //记录返回键点击次数
        mutableStateOf(0)
    }
    val ctx = LocalContext.current
    val toast = Toast.makeText(
        ctx,
        "再点击一次退出",
        Toast.LENGTH_SHORT
    )       //获取当前上下文
    BackHandler(onBack = {               //监听返回键点击
        val countDownTimer = object : CountDownTimer(2000, 2000) {
            override fun onTick(millisUntilFinished: Long) {    //使用定时器监听两秒内点击了几次返回键
                // 指定时间间隔触发的逻辑处理
                if (backPressedCount < 1) {
                    backPressedCount++
                    // 显示返回


                    toast.show()


                } else {
                    // 退出应用
                    toast.cancel()
                    val activity = ctx as? Activity
                    activity?.finish()

                }
            }

            override fun onFinish() {
                // 计时结束后触发的逻辑处理
                backPressedCount = 0
            }
        }

// 启动计时器
        countDownTimer.start()
    })

    var selectedTabIndex by remember { mutableStateOf(2) }


    Scaffold(
        bottomBar = {
            BottomBar(
                toFind = { selectedTabIndex = 0 },
                toLyc = { selectedTabIndex = 1 },
                toSongList = { selectedTabIndex = 2 },
                toTop = { selectedTabIndex = 3 },
                toMine = { selectedTabIndex = 4 },
                selectedTabIndex = selectedTabIndex
            )
        }
    ) {
        var extended by rememberSaveable {
            mutableStateOf(false)
        }
        val findviewModel by remember {
            mutableStateOf(FindviewModel())
        }
        val mediaPlayerViewModel by remember {
            mutableStateOf(MediaPlayerViewModel())
        }
        val searchViewModel by remember {
            mutableStateOf(SearchviewModel())
        }
        val loginViewModel by remember {
            mutableStateOf(LoginviewModel())
        }
        val pagerState = rememberPagerState(initialPage = selectedTabIndex)


        val name = findviewModel.currentMusic.value.name
        val artist = findviewModel.currentMusic.value.artist
        val duration = mediaPlayerViewModel.durationText
        val lycState = rememberSaveable { mutableStateOf("") }
        val lyc = lycState.value
        val lycstr = """   
            
[ar:$artist]
[ti:$name]
[al:]
[length:$duration]


""" + lyc
        val state = rememberLyricsViewState(lrcContent = lycstr, mediaPlayerViewModel)
        var showSearch = rememberSaveable {
            mutableStateOf(false)
        }


        LaunchedEffect(findviewModel.currentMusic.value.id) {
            var lycs = ""
            if (findviewModel.currentMusic.value.id.toInt() != 0)
                lycs = Repository().getCurrentMusicLyric(
                    findviewModel.currentMusic.value.id
                )
            lycState.value = lycs

        }
        LaunchedEffect(lycState.value) {
            state.play()
        }
        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }

        HorizontalPager(state = pagerState, pageCount = 5) { page ->

            AnimatedVisibility(visible = showSearch.value) {
                Search(
                    searchViewModel,
                    findviewModel = findviewModel,
                    mediaPlayerViewModel = mediaPlayerViewModel,
                    onBack = { showSearch.value = false })
            }

            when (page) {
                0 -> {
                    AnimatedVisibility(visible = !showSearch.value) {
                        Find(
                            findviewModel,
                            mediaPlayerViewModel,
                            state = state,
                            searchviewModel = searchViewModel,
                            { showSearch.value = true },
                        )
                    }
                }

                1 -> LyricPage(findviewModel, mediaPlayerViewModel, state)
                2 -> Login(loginviewModel = loginViewModel)

                3 -> Top()
                4 -> Mine()
            }
        }

        PlayButton(
            extended = extended,
            onClick = { extended = !extended },
            findviewModel = findviewModel,
            state = state,
            mediaPlayerViewModel = mediaPlayerViewModel
        )

    }
}


@Preview
@Composable
fun MyappPreview() {
    ViewModelListTheme {
        Myapp()
    }
}



