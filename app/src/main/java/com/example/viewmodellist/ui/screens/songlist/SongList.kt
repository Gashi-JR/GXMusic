package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LyricsViewPage
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.songListGradient
import com.example.viewmodellist.utils.Datamodels
import com.google.accompanist.pager.ExperimentalPagerApi
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun SongList(
    modifier : Modifier = Modifier,
    loginviewModel : LoginviewModel = LoginviewModel(),
    songListViewModel : SongListViewModel = SongListViewModel(),
    mediaPlayerViewModel : MediaPlayerViewModel = MediaPlayerViewModel(),
    findviewModel: FindviewModel = FindviewModel()
) {


    LaunchedEffect(loginviewModel.result.value.cookie){
        songListViewModel.fetchSongLists()
    }


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
                verticalAlignment = Alignment.CenterVertically,) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                    contentDescription = null)
                Text(text = "歌单",
                    color = Color.White,
                    modifier = modifier.padding(start = 10.dp),
                    fontWeight = FontWeight.Bold)

            }
            Row (verticalAlignment = Alignment.CenterVertically,){
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "search",
                    tint = Color.White
                )
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_more_vert_24),
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(25.dp))

        Row() {
            var coverImgUrl by remember { mutableStateOf(songListViewModel.coverImgUrl) }
/*            try{
                println("songlist中的专辑封面URL:" + coverImgUrl.value)
            }catch (e : Exception){
                println("song-list封面加载失败$e")
            }*/
            AsyncImage(
                model = coverImgUrl.value,
                contentDescription = "专辑封面",
                modifier = modifier
                    .height(100.dp)
                    .width(100.dp)
                    .padding(start = 8.dp)
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(text = "我喜欢的音乐",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color.White)
                Row(modifier = Modifier.padding(start = 8.dp)) {



                    Text("gnon2002",
                        modifier = modifier.padding(top = 5.dp),
                    color = Color.Gray,
                    fontWeight = FontWeight.Light
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                        contentDescription = null,
                        tint = Color.Gray.copy(alpha = 0.5f)
                    )
                }
            }
        }

        Spacer(modifier = modifier.height(10.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ){
            ElevatedButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_screen_share_24),
                    contentDescription = "分享")
                Text(text = "分享")
            }
            ElevatedButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_comment_24),
                    contentDescription = "评论")
                Text(text = "评论")
            }
            ElevatedButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_create_new_folder_24),
                    contentDescription = "收藏")
                Text(text = "收藏")
            }

        }


        //.border(20.dp,Color.Black, RoundedCornerShape(20.dp,20.dp,0.dp,0.dp)
        Spacer(modifier = Modifier.height(50.dp))

        Text(text = "  含126首VIP专享歌曲",
            modifier.fillMaxWidth()
            .clip(RoundedCornerShape(20.dp,20.dp))
            .background(Color.White))

        //TODO : 歌单部分

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.background(Color.White)){
            itemsIndexed(songListViewModel.songList){
                    index,songlist->
                SongItem(
                    index,
                    songlist.url,
                    songlist.name,
                    songlist.artist,
                modifier = Modifier.clickable{
                        findviewModel.currentMusic.value.id = songlist.id
                        findviewModel.currentMusic.value.name = songlist.name
                        findviewModel.currentMusic.value.picUrl = songListViewModel.coverImgUrl.value
                        findviewModel.currentMusic.value.artist = songlist.artist
                    mediaPlayerViewModel.play(songlist.url)

                })
            }
        }

    }

}

@Composable
fun SongItem(
    no : Int,
    url:String,
    name : String,
    author : String,
    Unit : () -> Unit = {},
    modifier: Modifier = Modifier
){
    Row(modifier = modifier
        .fillMaxWidth()
        .background(Color.White),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row {
            Text(
                (no+1).toString(),
                modifier = Modifier.padding(start = 12.dp)
                    .align(Alignment.CenterVertically),
            )

            Column(modifier = Modifier.padding(start = 12.dp)) {
                Text(text = name)
                Text(text = author,fontWeight = FontWeight.Light)
            }
        }


        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = "",
                tint = Color.Black.copy(alpha = 0.5f)
            )
        }
    }
    
    
}

@Preview
@Composable
fun SonglistPreview() {
    ViewModelListTheme {
        SongList()
    }
}