package com.example.viewmodellist

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.viewmodellist.ui.components.BottomBar
import com.example.viewmodellist.ui.navigation.NavGraph
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


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        bottomBar = {
            BottomBar(
                toFind = { NavGraph.toSingleInstance(navController, "find") },
                toMine = { NavGraph.toSingleInstance(navController, "mine") },
                toSongList = { NavGraph.toSingleInstance(navController, "songlist") },
                toTop = { NavGraph.toSingleInstance(navController, "top") },
                navHostController = navController
            )
        }
    ) {
        NavGraph.create(navHostController = navController)

    }
}


@Preview
@Composable
fun MyappPreview() {
    ViewModelListTheme {
        Myapp()
    }
}



