package com.example.viewmodellist.ui.screens.lyricsview


import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.ui.components.LyricsViewPage
import com.example.viewmodellist.ui.theme.ViewModelListTheme


@Composable
fun LyricPage() {

        LyricsViewPage()

}


@Preview
@Composable
fun LyricPagePreview() {
    ViewModelListTheme {
        LyricPage()
    }
}