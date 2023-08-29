package com.example.viewmodellist.ui.components.songlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.components.find.AlbumArt
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.songListGradient
import com.example.viewmodellist.utils.Datamodels

@Composable
fun RowThree1(albumArts : List<Datamodels.SongListItem>,
modifier : Modifier = Modifier,
songListViewModel : SongListViewModel) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp),
    modifier = modifier.padding(start = 7.dp)) {
        AlbumArt(
            id = albumArts[0].id,
            imageUrl = albumArts[0].picUrl,
            title = albumArts[0].name,
            playCounts = albumArts[0].playCount,
            songListViewModel = songListViewModel
        )
        AlbumArt(
            id = albumArts[1].id,
            imageUrl = albumArts[1].picUrl,
            title = albumArts[1].name,
            playCounts = albumArts[1].playCount,
            songListViewModel = songListViewModel

        )
        AlbumArt(
            id = albumArts[2].id,
            imageUrl = albumArts[2].picUrl,
            title = albumArts[2].name,
            playCounts = albumArts[2].playCount,
            songListViewModel = songListViewModel
        )
    }
}

@Composable
fun RowThree2(albumArts : List<Datamodels.HotPlayListItem>,
              songListViewModel : SongListViewModel,
              modifier : Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier.padding(start = 7.dp)
    ) {
        AlbumArt(
            id = albumArts[0].id,
            imageUrl = albumArts[0].coverImgUrl,
            title = albumArts[0].name,
            playCounts = albumArts[0].playCount,
            songListViewModel = songListViewModel
        )
        AlbumArt(
            id = albumArts[1].id,
            imageUrl = albumArts[1].coverImgUrl,
            title = albumArts[1].name,
            playCounts = albumArts[1].playCount,
            songListViewModel = songListViewModel

        )
        AlbumArt(
            id = albumArts[2].id,
            imageUrl = albumArts[2].coverImgUrl,
            title = albumArts[2].name,
            playCounts = albumArts[2].playCount,
            songListViewModel = songListViewModel
        )
    }
}