package com.example.viewmodellist.ui.components.songlist


import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel


//TODO 顶部栏

@Composable
fun TopBar(
    songListViewModel: SongListViewModel,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.padding(top = 8.dp)) {

        Spacer(modifier = modifier.height(6.dp))

        // TODO 歌单标签选择
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.Top,
            contentPadding = PaddingValues(horizontal = 15.dp),
        ) {
            itemsIndexed(songListViewModel.tagList) { index, item ->
                TagItem(
                    txt = item,
                    isSelected = index == songListViewModel.selectedTagIndex.value
                ) {
                    songListViewModel.nowTag.value = item
                    songListViewModel.selectedTagIndex.value = index
                    songListViewModel.fetchTagPlayList()
                    songListViewModel.isRec.value = !songListViewModel.isRec.value
                    println("Click on $item\n" + songListViewModel.isRec.value)
                }
            }
        }
    }
}

@Composable
fun TagItem(
    txt: String,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {

    Column(modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            // 长按事件
            onLongPress = {},
            // 点击事件
            onTap = { onItemClick() })
    }

    ) {
        val tagTextStyle = if (isSelected) {
            TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = Color.Black
            )
        } else {
            TextStyle(
                fontWeight = FontWeight.Light,
                fontSize = 17.sp,
                color = Color.Gray
            )
        }
        Box {


            if (isSelected) {
                Spacer(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.extraLarge)
                        .padding(top = 2.dp, bottom = 2.dp)
                        .background(Color(252, 92, 90))
                        .height(5.dp)
                        .width(35.dp)
                        .align(Alignment.BottomCenter)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 2.dp)
                        .height(5.dp)
                        .width(35.dp)
                        .align(Alignment.BottomCenter)
                )
            }
            Text(text = txt, style = tagTextStyle)
        }

    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(SongListViewModel())
}