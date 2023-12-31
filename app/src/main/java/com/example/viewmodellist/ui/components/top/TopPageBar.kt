package com.example.viewmodellist.ui.components.top


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.songlist.TagItem
import com.example.viewmodellist.ui.screens.top.TopviewModel


//TODO 顶部栏

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TopPageBar(
    tags: List<String> = listOf(),
    topviewModel: TopviewModel = TopviewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier,
) {
    val nowindex = rememberSaveable {
        mutableStateOf(0)
    }
    LaunchedEffect(topviewModel.nowindex) {
        nowindex.value = topviewModel.nowindex
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(255, 251, 255))
            .pointerInput(Unit) {
                // 拦截并消耗点击事件
                detectTapGestures { }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = modifier.height(6.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .background(Color(255, 251, 255)),
        ) {
            tags.forEachIndexed { index, item ->
                TagItem(
                    txt = item,
                    isSelected = index == nowindex.value
                ) {
                    nowindex.value = index
                    topviewModel.shouldUpdateIndex.value = true
                    topviewModel.nowindex = index
                }
            }
        }
    }
}


@Preview
@Composable
fun TopPageBarPreview() {
    TopPageBar()
}