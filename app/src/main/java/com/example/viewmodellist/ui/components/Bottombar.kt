package com.example.viewmodellist.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.navigation.NavGraph.selectedTabIndex
import com.example.viewmodellist.ui.screens.find.Find
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.songlist.SongList
import com.example.viewmodellist.ui.screens.top.Top
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomBar(
    toFind: () -> Unit,
    toMine: () -> Unit,
    toSongList: () -> Unit,
    toTop: () -> Unit,
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {

    var selectedindex by remember {
        mutableStateOf(0)
    }


    val titles = listOf(
        stringResource(id = R.string.app_find),
        stringResource(id = R.string.app_songlist),
        stringResource(id = R.string.app_top)
    )


    TabRow(
        selectedTabIndex = selectedTabIndex,
        modifier = Modifier.fillMaxWidth(),
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Color.Black,
                height = 2.dp
            )
        }
    ) {
        titles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                },
                text = {
                    Text(
                        text = title,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            )
        }
    }


}


//    BottomNavigation(
//        modifier = modifier,
//        backgroundColor = Color.White
//    ) {
//        BottomNavigationItem(selected = true, onClick = toFind, icon = {
//            Icon(imageVector = Icons.Default.Search, contentDescription = null)
//        }, label = {
//            Text(
//                text = stringResource(id = R.string.app_find),
//                fontSize = 10.sp,
//                color = Color.Gray
//            )
//        },
//            selectedContentColor = Color.Red
//        )
//        BottomNavigationItem(
//            selected = true,
//            onClick = toSongList,
//            icon = {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_music_note_24),
//                    contentDescription = null
//                )
//            },
//            label = {
//                Text(
//                    text = stringResource(id = R.string.app_songlist),
//                    fontSize = 10.sp,
//                    color = Color.Gray
//                )
//            }
//        )
//        BottomNavigationItem(selected = true, onClick = toTop, icon = {
//            Icon(
//                painter = painterResource(id = R.drawable.round_queue_music_24),
//                contentDescription = null
//            )
//        }, label = {
//            Text(
//                text = stringResource(id = R.string.app_top),
//                fontSize = 10.sp,
//                color = Color.Gray
//            )
//        }
//        )
//        BottomNavigationItem(selected = true, onClick = toMine, icon = {
//            Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
//        }, label = {
//            Text(
//                text = stringResource(id = R.string.app_mine),
//                fontSize = 10.sp,
//                color = Color.Gray
//            )
//        }
//        )
//    }
//}


@Preview
@Composable
fun BottombarPreview() {
    ViewModelListTheme {
        BottomBar(
            toFind = {},
            toMine = {},
            toSongList = {},
            toTop = {},
            navHostController = rememberNavController()
        )
    }
}
