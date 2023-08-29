package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.find.AlbumArt
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel


@Composable
fun OtherTags(
    songListViewModel: SongListViewModel,
    tag : String){

    LazyVerticalGrid(columns = GridCells.Fixed(3),
    verticalArrangement = Arrangement.spacedBy(6.dp)){
        items(songListViewModel.tagPlayList){
            item ->
            AlbumArt(id = item.id,
                imageUrl = item.coverImgUrl,
                title = item.name,
                playCounts = item.playCount,
                songListViewModel = songListViewModel)
        }
    }
}