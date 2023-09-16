package com.example.viewmodellist.ui.components.songlist

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.utils.formatter

@Composable
fun SongListSquare(
    songListViewModel: SongListViewModel = SongListViewModel(),
    loginviewModel: LoginviewModel,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    val showrec = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(Unit) {
        if (songListViewModel.songlistData.isEmpty())
            songListViewModel.fetchRecommendSonglistData()
        if (songListViewModel.hotPlayList.isEmpty())
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
        Spacer(
            modifier = Modifier
                .height(formatter.mainActivity?.getStatusBarHeight()!!.dp)
                .fillMaxWidth()
        )

        TopBar(songListViewModel)

        AnimatedVisibility(
            visible = showrec.value, enter = slideInVertically(
                initialOffsetY = { -it }
            ),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {
            RecSongList(
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
