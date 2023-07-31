package com.example.viewmodellist.ui.screens.search

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.LyricsViewPage
import com.example.viewmodellist.ui.components.OptionButton
import com.example.viewmodellist.ui.components.find.TopAppBar
import com.example.viewmodellist.ui.theme.ViewModelListTheme
import com.example.viewmodellist.ui.theme.borderGradient
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.zIndex
import com.example.viewmodellist.ui.components.Tag
import com.example.viewmodellist.ui.components.find.FindCard
import com.example.viewmodellist.ui.components.search.SearchHotTopItem
import com.example.viewmodellist.ui.components.search.SearchResult

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun Search(
    searchviewModel: SearchviewModel,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchValue by remember {
        mutableStateOf("")
    }
    var isShow by remember {
        mutableStateOf(false)
    }
    var isResult by remember {
        mutableStateOf(true)
    }

    val focusManager = LocalFocusManager.current
    val isFocused = remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        //  searchviewModel.fetchSearchHotData()
        //searchviewModel.fetchSearchHotTopData()
    }


    LaunchedEffect(searchValue) {
        if (searchValue == "")
            isShow = false
    }

    Box(modifier = modifier.fillMaxHeight()) {
        Column(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    onClick = if (isResult) {
                        { isResult = false }
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

                    TextField(
                        value = searchValue,
                        onValueChange = {
                            searchValue = it
                            searchviewModel.fetchSearchSuggestData(searchValue)
                            isShow = true
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_search_24),
                                contentDescription = "search"
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
                                    painter = painterResource(id = R.drawable.baseline_clear_24),
                                    contentDescription = "clear",
                                )
                            }
                        },
                        maxLines = 1,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Color(0, 0, 0, 0),
                            cursorColor = Color.Gray,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(Color(0, 0, 0, 0))
                            .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                            .clip(MaterialTheme.shapes.medium),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions =
                        KeyboardActions(onSearch = { /* TODO: Handle search action */ }),
                    )


                }









                Button(
                    onClick = {
                        isResult = true
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









            AnimatedVisibility(visible = !isResult) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    item {
                        FindCard(
                            title = R.string.search_hotkey,
                            false,
                            false,
                            true,
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
                                        onClick = {},
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
                            false,
                            false,
                            true,
                            modifier = Modifier.padding(horizontal = 15.dp)
                        ) {
                            Spacer(modifier = Modifier.height(15.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                searchviewModel.searchHotTopData.forEach { item ->
                                    SearchHotTopItem(
                                        score = item.score,
                                        searchWord = item.searchWord,
                                        iconUrl = item.iconUrl,
                                        content = item.content,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {}
                                    )
                                }
                            }
                        }
                    }
                }
            }

            AnimatedVisibility(visible = isResult) {
                SearchResult()
            }

        }
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .absoluteOffset(y = 60.dp)
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
                                    headlineText = {
                                        Text(
                                            item.keyword,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }, modifier = Modifier
                                        .width(240.dp)
                                        .clickable { }
                                )
                            }

                        }
                    }
                }


        }
    }


}


@Preview
@Composable
fun SearchPreview() {
    ViewModelListTheme {
        Search(SearchviewModel())
    }
}