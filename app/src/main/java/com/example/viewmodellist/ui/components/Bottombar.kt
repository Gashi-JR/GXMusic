package com.example.viewmodellist.ui.components

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@Composable
fun BottomBar(
    toFind: () -> Unit,
    toMine: () -> Unit,
    toLyc: () -> Unit,
    toSongList: () -> Unit,
    toTop: () -> Unit,
    selectedTabIndex: Int,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White
    ) {
        BottomNavigationItem(
            selected = selectedTabIndex == 0, onClick = toFind,
            icon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_find),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },
            selectedContentColor = Color.Red,

            )
        BottomNavigationItem(
            selected = selectedTabIndex == 1,
            onClick = toLyc,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_lyrics_24),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_lyricsview),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },
            selectedContentColor = Color.Red
        )
        BottomNavigationItem(
            selected = selectedTabIndex == 2,
            onClick = toSongList,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_music_note_24),
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_songlist),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },
            selectedContentColor = Color.Red
        )

        BottomNavigationItem(selected = selectedTabIndex == 3, onClick = toTop, icon = {
            Icon(
                painter = painterResource(id = R.drawable.round_queue_music_24),
                contentDescription = null
            )
        }, label = {
            Text(
                text = stringResource(id = R.string.app_top),
                fontSize = 10.sp,
                color = Color.Gray
            )
        },
            selectedContentColor = Color.Red
        )
        BottomNavigationItem(selected = selectedTabIndex == 4, onClick = toMine, icon = {
            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
        }, label = {
            Text(
                text = stringResource(id = R.string.app_mine),
                fontSize = 10.sp,
                color = Color.Gray
            )
        },
            selectedContentColor = Color.Red
        )
    }
}


@Preview
@Composable
fun BottombarPreview() {
    ViewModelListTheme {
        BottomBar(
            toFind = {},
            toMine = {},
            toLyc = {},
            toSongList = {},
            toTop = {},
            selectedTabIndex = 0
        )
    }
}
