package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LyricsViewPage
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.songListGradient
import com.example.viewmodellist.utils.Datamodels
import com.google.accompanist.pager.ExperimentalPagerApi

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun SongList(
    modifier : Modifier = Modifier,
    imageUrl : String,

) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(songListGradient)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.padding(start = 15.dp)) {
                Text("歌单")
            }
            Row (verticalAlignment = Alignment.CenterVertically,){
                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "search",
                    tint = Color.Black.copy(alpha = 0.5f)
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_vert_24),
                        contentDescription = "",
                        tint = Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(30.dp))

        Row() {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = modifier
                    .height(100.dp)
                    .width(100.dp)
            )
            Column() {
                Text(text = "我喜欢的音乐")
                Row {

                    Text("gnon2002")
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                        contentDescription = null,
                        tint = Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(30.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ){
            Text(text = "分享")
            Text(text = "评论")
            Text(text = "分享")
        }

    }

}



@Preview
@Composable
fun SonglistPreview() {
    ViewModelListTheme {
        SongList(imageUrl = "http://p2.music.126.net/a9oLdcFPhqQyuouJzG2mAQ==/3273246124149810.jpg")
    }
}