package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.songlist.SongListDetail
import com.example.viewmodellist.ui.components.songlist.SongListSquare
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.login.LoginviewModel

@Composable
fun SongList(
    songListViewModel : SongListViewModel = SongListViewModel(),
    mediaPlayerViewModel : MediaPlayerViewModel = MediaPlayerViewModel(),
    findviewModel: FindviewModel = FindviewModel(),
    loginviewModel : LoginviewModel = LoginviewModel(),
    modifier : Modifier = Modifier
) {

    //歌单广场
    AnimatedVisibility(visible = !songListViewModel.isShowDetail.value) {
        SongListSquare(findviewModel,mediaPlayerViewModel,songListViewModel,loginviewModel)
    }

    //歌单详情
    AnimatedVisibility(visible = songListViewModel.isShowDetail.value) {
        SongListDetail(loginviewModel, songListViewModel,mediaPlayerViewModel,findviewModel)
    }
}