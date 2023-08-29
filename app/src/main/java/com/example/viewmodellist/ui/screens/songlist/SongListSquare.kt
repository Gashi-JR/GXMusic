package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.songlist.OtherTags
import com.example.viewmodellist.ui.components.songlist.RecSongList
import com.example.viewmodellist.ui.components.songlist.TopBar
import com.example.viewmodellist.ui.screens.find.FindviewModel

@Composable
fun SongListSquare(
    findviewModel : FindviewModel = FindviewModel(),
    mediaPlayerViewModel : MediaPlayerViewModel = MediaPlayerViewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    modifier: Modifier = Modifier
    ) {

    LaunchedEffect(Unit) {
        songListViewModel.fetchRecommendSonglistData()
        songListViewModel.fetchHotPlaylist()
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .background(Color.White)
            .padding(start = 7.dp, end = 7.dp)
    ) {
        TopBar(songListViewModel)
        AnimatedVisibility(visible = !songListViewModel.isShowDetail.value && songListViewModel.selectedTagIndex.value == 0) {
            RecSongList(
                findviewModel,
                mediaPlayerViewModel,
                songListViewModel,
            )
        }
        AnimatedVisibility(visible = !songListViewModel.isShowDetail.value&& songListViewModel.selectedTagIndex.value != 0) {
            OtherTags(songListViewModel = songListViewModel, tag = "欧美")
        }
    }
}
