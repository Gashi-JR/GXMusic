package com.example.viewmodellist.ui.screens.songlist

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@Composable
fun SongList() {
    Text(text = "songlist")
}


@Preview
@Composable
fun SonglistPreview() {
    ViewModelListTheme {
        SongList()
    }
}