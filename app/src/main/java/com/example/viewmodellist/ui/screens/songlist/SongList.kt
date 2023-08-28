package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.songlist.RecSongList
import com.example.viewmodellist.ui.components.songlist.TopBar
import com.example.viewmodellist.ui.screens.find.FindviewModel

@Composable
fun SongList(
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
        modifier = modifier.background(Color.White)
            .padding(start = 7.dp,end = 7.dp)
    ) {
        TopBar()
        RecSongList(findviewModel,mediaPlayerViewModel,songListViewModel)
    }
}
