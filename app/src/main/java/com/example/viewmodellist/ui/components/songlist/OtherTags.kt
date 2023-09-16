package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.LoadingAnimation
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel


@Composable
fun OtherTags(
    songListViewModel: SongListViewModel
) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 15.dp),
        horizontalArrangement = Arrangement.spacedBy(7.dp)
    ) {

        if (songListViewModel.tagPlayList.isNotEmpty()) {
            items(songListViewModel.tagPlayList) { item ->
                AlbumArt(
                    id = item.id,
                    imageUrl = item.coverImgUrl,
                    title = item.name,
                    playCounts = item.playCount,
                    songListViewModel = songListViewModel
                )
            }

        } else {
            items(List(15) { 1 })
            {
                LoadingAnimation(
                    modifier = Modifier
                        .width(120.dp)
                        .height(160.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        }
        item {
            Spacer(
                modifier = Modifier
                    .height(55.dp)
            )
        }
        item {
            Spacer(
                modifier = Modifier
                    .height(55.dp)
            )
        }
    }
}