package com.example.viewmodellist.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                if (selectedTabIndex == 0)
                    Icon(
                        painterResource(id = R.drawable.find),
                        contentDescription = null,
                        tint = Color(250, 65, 64),
                        modifier = Modifier.size(24.dp)
                    )
                else {
                    Icon(
                        painterResource(id = R.drawable.find1),
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_find),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }

        )
        BottomNavigationItem(
            selected = selectedTabIndex == 1,
            onClick = toLyc,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.lyc),
                    contentDescription = null,
                    tint = if (selectedTabIndex == 1) Color(250, 65, 64) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_lyricsview),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
        )
        BottomNavigationItem(
            selected = selectedTabIndex == 2,
            onClick = toSongList,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.list),
                    contentDescription = null,
                    tint = if (selectedTabIndex == 2) Color(250, 65, 64) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_songlist),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },

            )

        BottomNavigationItem(
            selected = selectedTabIndex == 3, onClick = toTop,
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.top),
                    contentDescription = null,
                    tint = if (selectedTabIndex == 3) Color(250, 65, 64) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_top),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },

            )
        BottomNavigationItem(
            selected = selectedTabIndex == 4, onClick = toMine,
            icon = {
                Icon(
                    painterResource(id = R.drawable.mine),
                    contentDescription = null,
                    tint = if (selectedTabIndex == 4) Color(250, 65, 64) else Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.app_mine),
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },

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
