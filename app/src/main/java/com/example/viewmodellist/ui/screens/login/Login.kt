package com.example.viewmodellist.ui.screens.login

import android.graphics.BitmapFactory
import android.service.controls.ControlsProviderService.TAG
import android.util.Base64
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import kotlinx.coroutines.delay


@Composable
fun Login(loginviewModel: LoginviewModel, onLogin: () -> Unit = {}, modifier: Modifier = Modifier) {
    var isRefresh by remember {
        mutableStateOf(true)
    }
    var isOverdue by remember {
        mutableStateOf(false)
    }
    var isEnter by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(isRefresh) {
        if (!isRefresh) { //轮询扫码登录状态
            while (true) {
                loginviewModel.fetchLoginStatus()
                delay(2000)
                when (loginviewModel.result.value.code) {
                    800 -> {                //二维码过期
                        isRefresh = true
                        isOverdue = true
                        break
                    }

                    801 -> {}               //等待扫码
                    802 -> {}              //已扫码等待授权
                    803 -> {              //已授权登录
// TODO:  登录成功后获取cookie解析，跳转主页面然后加载用户信息
                        onLogin()
                        loginviewModel.fetchUserUserInfo()
                        isOverdue = true
                        break
                    }
                }
            }

        }
    }

    LaunchedEffect(loginviewModel.qrimg.value) {
        isOverdue = false
    }

    LaunchedEffect(Unit) {
        isEnter = true
    }
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 70.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(90.dp))
        AnimatedVisibility(
            visible = isEnter,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            ),
            exit = slideOutVertically(targetOffsetY = { -it })
        ) {
            Image(
                painter = painterResource(id = R.drawable.qq20230804133953),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )
        }

        AnimatedVisibility(visible = loginviewModel.qrimg.value != "") {

            Log.d(TAG, "Login: ${loginviewModel.qrimg}")
            val decodedBytes = Base64.decode(
                loginviewModel.qrimg.value.split(",")[1], Base64.DEFAULT
            )
            val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)

            AnimatedVisibility(visible = !isOverdue) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "验证码",
                    modifier = Modifier.size(300.dp)
                )
            }

            AnimatedVisibility(visible = isOverdue) {
                Image(
                    painter = painterResource(id = R.drawable.qq20230804161952),
                    contentDescription = null,
                    modifier = Modifier.size(300.dp)
                )
                Text(
                    text = loginviewModel.result.value.message + ",请重新生成",
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }

        Spacer(modifier = Modifier.height(50.dp))



        AnimatedVisibility(
            visible = isEnter,
            enter = slideInHorizontally(
                initialOffsetX = { it }, animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            ),
            exit = slideOutHorizontally(targetOffsetX = { -it })
        ) {
            Button(
                onClick = {
                    loginviewModel.fetchLoginQRcode()
                    isRefresh = false
                },
                elevation = ButtonDefaults.elevation(0.dp),
                enabled = isRefresh,
                shape = MaterialTheme.shapes.extraLarge,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0, 160, 218)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
            ) {

                Text(
                    text = "点击生成二维码",
                    color = Color.White,
                    fontSize = 17.sp
                )
            }
        }

    }

}

@Preview
@Composable
fun LoginPreview() {
    ViewModelListTheme {
        Login(LoginviewModel())
    }

}