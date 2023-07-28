package com.example.viewmodellist.ui.screens.lyricsview


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.dokar.amlv.LyricsViewState
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
    mediaPlayerViewModel: MediaPlayerViewModel,
    state: LyricsViewState,

    ) {
    val isfirst = rememberSaveable() {
        mutableStateOf(true)

    }
    LaunchedEffect(mediaPlayerViewModel.isPlaying) {
        if (!isfirst.value) {
            if (mediaPlayerViewModel.isPlaying)
                mediaPlayerViewModel.pause()
            else
                mediaPlayerViewModel.resume()
            mediaPlayerViewModel.isPlaying = !mediaPlayerViewModel.isPlaying
            isfirst.value = false
        }

    }



    LyricsViewPage(
        picUrl = findviewModel.currentMusic.value.picUrl,
        findviewModel = findviewModel,
        mediaPlayerViewModel = mediaPlayerViewModel,
        state = state
    )


}


