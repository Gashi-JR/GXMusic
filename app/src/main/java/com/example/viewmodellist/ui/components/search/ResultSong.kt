package com.example.viewmodellist.ui.components.search


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.ui.components.songlist.SongItem
import com.example.viewmodellist.utils.Datamodels


@Composable
fun ResultSong(
    index: Int,
    song: Datamodels.ResultSong,
    isSelected: Boolean = false,
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    SongItem(
        no = index,
        name = song.name,
        author = song.artist,
        fee = -1,
        isSelected = isSelected,
        modifier = modifier
    )

}


@Preview(showBackground = true)
@Composable
fun ResultSongPreview() {
    ResultSong(
        0,
        Datamodels.ResultSong(1, 17994578437, 1, ",", "d", "")
    )
}

