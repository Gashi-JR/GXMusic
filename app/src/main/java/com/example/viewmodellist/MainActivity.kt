package com.example.viewmodellist


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.dokar.amlv.rememberLyricsViewState
import com.example.viewmodellist.ui.components.BottomBar
import com.example.viewmodellist.ui.components.Message
import com.example.viewmodellist.ui.components.PlayButton
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.Find
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.ui.screens.login.Login
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.lyricsview.LyricPage
import com.example.viewmodellist.ui.screens.mine.Mine
import com.example.viewmodellist.ui.screens.mine.MineviewModel
import com.example.viewmodellist.ui.screens.search.Search
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.screens.songlist.SongList
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.screens.top.Top
import com.example.viewmodellist.ui.screens.top.TopviewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.utils.formatter


class MainActivity : ComponentActivity() {

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        formatter.mainActivity = this

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            ViewModelListTheme {

                Surface(

                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {


                        Myapp()

                    }

                }


            }
        }
    }

    override fun onResume() {
        super.onResume()
        setSystemBarsTransparent()
    }


    @SuppressLint("WrongConstant", "RememberReturnType")
    private fun setSystemBarsTransparent() {            //沉浸式状态栏
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.let {
            ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
                val systemWindowInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                view.updatePadding(bottom = systemWindowInsets.bottom)
                insets
            }
        }
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getStatusBarHeight(): Int {                 //获取状态栏dp值
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        val density = resources.displayMetrics.density
        return (statusBarHeight / density).toInt()
    }

}


@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation",
    "UnrememberedMutableState"
)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Myapp() {
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
    val mineviewModel by remember {
        mutableStateOf(MineviewModel())
    }
    val topviewModel by remember {
        mutableStateOf(TopviewModel())
    }

    val songListViewModel by remember { mutableStateOf(SongListViewModel()) }

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


    var isLogin by rememberSaveable {
        mutableStateOf(false)
    }
    val showSearch = rememberSaveable {
        mutableStateOf(false)
    }

    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val pagerState = rememberPagerState(initialPage = selectedTabIndex)
    var shouldUpdateIndex by remember { mutableStateOf(true) }

    LaunchedEffect(selectedTabIndex) {
        pagerState.scrollToPage(selectedTabIndex)
        // pagerState.animateScrollToPage(selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        if (shouldUpdateIndex) {
            selectedTabIndex = pagerState.currentPage
        }
        shouldUpdateIndex = false
    }
    Scaffold(
        bottomBar = {
            AnimatedVisibility(visible = !isLogin && !showSearch.value) {
                BottomBar(
                    toFind = { selectedTabIndex = 0 },
                    toLyc = { selectedTabIndex = 1 },
                    toSongList = { selectedTabIndex = 2 },
                    toTop = { selectedTabIndex = 3 },
                    toMine = { selectedTabIndex = 4 },
                    selectedTabIndex = selectedTabIndex
                )
            }

        }
    ) {
        var extended by rememberSaveable {
            mutableStateOf(false)
        }


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



        AnimatedVisibility(visible = isLogin) {
            Login(loginviewModel = loginViewModel, onLogin = {
                isLogin = false
            })
        }
        HorizontalPager(state = pagerState, pageCount = 5) { page ->

            AnimatedVisibility(visible = showSearch.value) {
                Search(
                    searchViewModel,
                    findviewModel = findviewModel,
                    mediaPlayerViewModel = mediaPlayerViewModel,
                    songListViewModel = songListViewModel,
                    onBack = { showSearch.value = false }

                )
            }

            when (page) {
                0 -> {
                    AnimatedVisibility(visible = !showSearch.value && !isLogin) {
                        Find(
                            findviewModel,
                            mediaPlayerViewModel,
                            searchviewModel = searchViewModel,
                            loginviewModel = loginViewModel,
                            songListViewModel = songListViewModel,
                            toSonglist = { selectedTabIndex = 2 },
                            toTop = { selectedTabIndex = 3 },
                            toFind = {
                                selectedTabIndex = 0
                            },
                            showSearch = { showSearch.value = true }
                        )
                    }
                    SideEffect {
                        shouldUpdateIndex = true
                    }
                }

                1 -> {
                    LyricPage(findviewModel, mediaPlayerViewModel, state)
                    SideEffect {
                        shouldUpdateIndex = true
                    }
                }

                2 -> {
                    SongList(songListViewModel, mediaPlayerViewModel, findviewModel)
                    SideEffect {
                        shouldUpdateIndex = true
                    }
                }

                3 -> {
                    Top(
                        findviewModel,
                        topviewModel,
                        songListViewModel = songListViewModel,
                        toSonglist = { selectedTabIndex = 2 },
                    ) { selectedTabIndex = 3 }
                    SideEffect {
                        shouldUpdateIndex = true
                    }
                }

                4 -> {
                    AnimatedVisibility(visible = !showSearch.value && !isLogin) {
                        Mine(
                            loginViewModel,
                            mineviewModel,
                            songListViewModel = songListViewModel,
                            onLogin = {
                                isLogin = true
                                loginViewModel.qrimg.value = ""
                            },
                            toMine = {
                                selectedTabIndex = 4
                            },
                            toSonglist = { selectedTabIndex = 2 },
                        )
                    }
                    SideEffect {
                        shouldUpdateIndex = true
                    }
                }
            }
        }

        AnimatedVisibility(!isLogin) {
            PlayButton(
                extended = extended,
                onClick = {
                    extended = !extended
                },
                findviewModel = findviewModel,
                state = state,
                mediaPlayerViewModel = mediaPlayerViewModel
            )
        }


        val show = remember {
            mutableStateOf(false)
        }

        if (show.value) {
            val countDownTimer = object : CountDownTimer(2000, 2000) {
                override fun onTick(millisUntilFinished: Long) {}
                override fun onFinish() {
                    // 计时结束后触发的逻辑处理
                    show.value = false
                }
            }
            // 启动计时器
            countDownTimer.start()
        }

        //type: 0成功，1警告，2错误
        Message(show = show.value, type = 1, message = "发放服务器而4444444444444")
    }
}


@Preview
@Composable
fun MyappPreview() {
    ViewModelListTheme {
        Myapp()
    }
}



