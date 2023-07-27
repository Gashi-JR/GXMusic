package com.example.viewmodellist.ui.screens.lyricsview


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.ui.components.LyricsViewPage
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.find.Repository
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LyricPage(
    findviewModel: FindviewModel,
    mediaPlayerViewModel: MediaPlayerViewModel
) {

    val lycState = rememberSaveable { mutableStateOf("") }

    LaunchedEffect(findviewModel.currentMusic.value.id) {
        var lyc = ""
        if (findviewModel.currentMusic.value.id.toInt() != 0)
            lyc = Repository().getCurrentMusicLyric(
                findviewModel.currentMusic.value.id
            )
        lycState.value = lyc
    }

    LyricsViewPage(
        name = findviewModel.currentMusic.value.name,
        artist = findviewModel.currentMusic.value.artist,
        duration = mediaPlayerViewModel.durationText,
        picUrl=findviewModel.currentMusic.value.picUrl,
        lyc = lycState.value,
        findviewModel = findviewModel,
        mediaPlayerViewModel = mediaPlayerViewModel
    )


}


@Preview
@Composable
fun LyricPagePreview() {
    ViewModelListTheme {
        LyricPage(FindviewModel(), MediaPlayerViewModel())
    }
}