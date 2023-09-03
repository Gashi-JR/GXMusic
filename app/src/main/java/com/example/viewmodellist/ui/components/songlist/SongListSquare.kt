package com.example.viewmodellist.ui.components.songlist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import java.util.Collections.list

@Composable
fun SongListSquare(
    findviewModel: FindviewModel = FindviewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    songListViewModel: SongListViewModel = SongListViewModel(),
    loginviewModel: LoginviewModel,
    modifier: Modifier = Modifier
) {
    val showrec = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        songListViewModel.fetchRecommendSonglistData()
        songListViewModel.fetchHotPlaylist()
        showrec.value = true
    }
    LaunchedEffect(songListViewModel.selectedTagIndex.value) {
        showrec.value = songListViewModel.selectedTagIndex.value == 0
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.fillMaxWidth()

    ) {
        TopBar(songListViewModel)

        AnimatedVisibility(
            visible = showrec.value, enter = slideInVertically(
                initialOffsetY = { -it }
            ),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            RecSongList(
                findviewModel,
                mediaPlayerViewModel,
                songListViewModel,
                loginviewModel
            )
        }

        songListViewModel.tagList.forEachIndexed { index, _ ->
            AnimatedVisibility(
                visible = songListViewModel.selectedTagIndex.value == index,
                enter = slideInVertically(
                    initialOffsetY = { -it }
                ),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                OtherTags(songListViewModel = songListViewModel)
            }
        }
    }
}
