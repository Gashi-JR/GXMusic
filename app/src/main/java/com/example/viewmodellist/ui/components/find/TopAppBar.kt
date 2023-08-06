package com.example.viewmodellist.ui.components.find


import android.app.Activity
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.OptionButton
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.theme.borderGradient
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay


@Composable
fun TopAppBar(
    searchviewModel: SearchviewModel = SearchviewModel(),
    loginviewModel: LoginviewModel = LoginviewModel(),
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var i by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(i, searchviewModel.searchHotData) {
        if (searchviewModel.searchHotData.isNotEmpty()) {
            if (i < searchviewModel.searchHotData.size - 1) {
                delay(8000)
                i++
            }
            if (i == searchviewModel.searchHotData.size - 1)
                i = 0
        }
    }

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        OptionButton(
            modifier = modifier
                .height(50.dp)
                .padding(top = 10.dp),
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
            modifier = Modifier
                .weight(1F)
                .height(50.dp)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClick() },

            ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                        .fillMaxHeight()
                ) {

                    androidx.compose.material.Icon(
                        painter = painterResource(id = R.drawable.baseline_search_24),
                        contentDescription = "search"
                    )

                    Text(
                        text = "大家都在搜   ${if (searchviewModel.searchHotData.isNotEmpty()) searchviewModel.searchHotData[i].first else ""} ",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = "clear"
                )
            }


        }

        AnimatedVisibility(visible = loginviewModel.User.value.avatarUrl != "") {
            Image(
                painter = rememberAsyncImagePainter(loginviewModel.User.value.avatarUrl),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
            )
        }

        Spacer(modifier = Modifier.width(15.dp))
    }
}


@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar()
}