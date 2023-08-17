package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import com.example.viewmodellist.ui.components.LyricsViewPage
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.google.accompanist.pager.ExperimentalPagerApi


@OptIn(ExperimentalPagerApi::class, ExperimentalCoilApi::class)
@Composable
fun SongList(
    modifier : Modifier = Modifier
) {
    Text(text = "sdfgs")
}


@Preview
@Composable
fun SonglistPreview() {
    ViewModelListTheme {
        SongList()
    }
}