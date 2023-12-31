package com.example.viewmodellist.ui.screens.search


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.Tag
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.find.MediaPlayerViewModel
import com.example.viewmodellist.ui.components.search.SearchHotTopItem
import com.example.viewmodellist.ui.components.search.SearchResult
import com.example.viewmodellist.ui.components.songlist.SongListDetail
import com.example.viewmodellist.ui.screens.find.FindviewModel
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.borderGradient
import com.example.viewmodellist.utils.formatter

@OptIn(
    ExperimentalLayoutApi::class
)
@Composable
fun Search(
    searchviewModel: SearchviewModel,
    onBack: () -> Unit = {},
    findviewModel: FindviewModel = FindviewModel(),
    mediaPlayerViewModel: MediaPlayerViewModel = MediaPlayerViewModel(),
    songListViewModel: SongListViewModel,
    loginviewModel: LoginviewModel = LoginviewModel(),
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    var searchValue by rememberSaveable {
        mutableStateOf("")
    }
    var search by rememberSaveable {
        mutableStateOf("")
    }
    var isShow by rememberSaveable {
        mutableStateOf(false)
    }
    var isResult by rememberSaveable {
        mutableStateOf(false)
    }

    val focusManager = LocalFocusManager.current


    LaunchedEffect(Unit) {
        if (searchviewModel.searchHotTopData.isEmpty())
            searchviewModel.fetchSearchHotTopData()
    }


    LaunchedEffect(searchValue) {
        if (searchValue == "")
            isShow = false
    }
    AnimatedVisibility(visible = !songListViewModel.isShowDetail.value) {
        Box(modifier = modifier.fillMaxHeight()) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier
                        .height(formatter.mainActivity?.getStatusBarHeight()!!.dp)
                        .fillMaxWidth()
                )

                Row(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = if (isResult) {
                            {
                                isResult = false
                                searchValue = ""
                                focusManager.clearFocus()
                            }
                        } else onBack
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                            contentDescription = "search",
                            modifier = Modifier
                                .size(30.dp)
                        )
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                        modifier = Modifier
                            .weight(1F)
                            .height(50.dp)
                            .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                            .clip(MaterialTheme.shapes.medium),

                        ) {

                        val containerColor = Color(0, 0, 0, 0)
                        TextField(
                            value = searchValue,
                            onValueChange = {
                                searchValue = it
                                searchviewModel.fetchSearchSuggestData(searchValue)
                                isShow = true
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(id = R.drawable.search),
                                    contentDescription = "search",
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        searchValue = ""
                                        focusManager.clearFocus()
                                    }

                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.close),
                                        contentDescription = "clear",
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            },
                            maxLines = 1,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = containerColor,
                                unfocusedContainerColor = containerColor,
                                disabledContainerColor = containerColor,
                                cursorColor = Color.Gray,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .background(Color(0, 0, 0, 0))
                                .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                                .clip(MaterialTheme.shapes.medium),
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions =
                            KeyboardActions(onSearch = {
                                search = searchValue
                                isResult = true
                                isShow = false
                                focusManager.clearFocus()
                            }),
                        )


                    }

                    Button(
                        onClick = {
                            if (searchValue != "") {
                                search = searchValue
                                isResult = true
                                isShow = false
                                focusManager.clearFocus()
                            }

                        }, colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0, 0, 0, 0),
                            // contentColor = Color(0, 0, 0, 0)
                        ), modifier = Modifier,
                        elevation = ButtonDefaults.elevation(0.dp),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "搜索")
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(formatter.mainActivity?.getStatusBarHeight()!!.dp)
                        .fillMaxWidth()
                )

                if (!isResult) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        item {
                            FindCard(
                                title = R.string.search_hotkey,
                                showmore = false,
                                showarrow = false,
                                showline = true,
                                modifier = Modifier.padding(horizontal = 15.dp)
                            ) {
                                Spacer(modifier = Modifier.height(15.dp))
                                FlowRow(
                                    maxItemsInEachRow = 5,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    searchviewModel.searchHotData.forEach { item ->
                                        Tag(
                                            onClick = {
                                                searchValue = item.first
                                                search = item.first
                                                isShow = false
                                                isResult = true
                                                focusManager.clearFocus()
                                            },
                                            backgroundColor = Color.Gray.copy(0.2f),
                                            modifier = Modifier
                                                .height(20.dp)
                                                .padding(bottom = 5.dp)
                                        ) {
                                            Text(
                                                text = item.first,
                                                color = Color.Black,
                                                fontSize = 10.sp,
                                            )
                                        }
                                    }
                                }
                            }

                        }
                        item {
                            FindCard(
                                title = R.string.search_hottop,
                                showmore = false,
                                showarrow = false,
                                showline = true,
                                modifier = Modifier.padding(horizontal = 15.dp),
                                icon = {
                                    androidx.compose.material3.Icon(
                                        painter = painterResource(id = R.drawable.h),
                                        contentDescription = null,
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            ) {
                                Spacer(modifier = Modifier.height(15.dp))
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(10.dp),
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    searchviewModel.searchHotTopData.forEach { item ->
                                        SearchHotTopItem(
                                            score = item.score,
                                            searchWord = item.searchWord,
                                            iconUrl = item.iconUrl,
                                            content = item.content,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    searchValue = item.searchWord
                                                    search = item.searchWord
                                                    isShow = false
                                                    isResult = true
                                                    focusManager.clearFocus()
                                                }
                                        )
                                    }
                                }
                            }
                        }
                    }
                } else {

                    SearchResult(
                        searchviewModel = searchviewModel,
                        keyword = search,
                        findviewModel, mediaPlayerViewModel, songListViewModel
                    )

                }

            }
            Column(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .absoluteOffset(y = 91.dp)
            ) {

                if (searchviewModel.searchSuggestData.isNotEmpty())
                    AnimatedVisibility(visible = isShow) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
                            modifier = Modifier
                                .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                                .clip(MaterialTheme.shapes.medium)
                                .zIndex(1f),

                            ) {
                            searchviewModel.searchSuggestData.forEach { item ->
                                run {
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                item.keyword,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }, modifier = Modifier
                                            .width(240.dp)
                                            .clickable {
                                                searchValue = item.keyword
                                                search = item.keyword
                                                isShow = false
                                                isResult = true
                                                focusManager.clearFocus()
                                            }
                                    )
                                }

                            }
                        }
                    }


            }
        }
    }

    //歌单详情
    AnimatedVisibility(visible = songListViewModel.isShowDetail.value) {
        SongListDetail(
            loginviewModel,
            songListViewModel,
            mediaPlayerViewModel,
            findviewModel
        )
    }

}


@Preview
@Composable
fun SearchPreview() {
    ViewModelListTheme {
        //  Search(SearchviewModel())
    }
}