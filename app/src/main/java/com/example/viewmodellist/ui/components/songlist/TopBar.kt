package com.example.viewmodellist.ui.components.songlist



import android.app.Activity
import android.os.CountDownTimer
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.OptionButton
import com.example.viewmodellist.ui.screens.login.LoginviewModel
import com.example.viewmodellist.ui.screens.search.SearchviewModel
import com.example.viewmodellist.ui.screens.songlist.SongListViewModel
import com.example.viewmodellist.ui.theme.borderGradient
import kotlinx.coroutines.Delay
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay


//TODO 顶部栏

@Composable
fun TopBar(
    songListViewModel: SongListViewModel = SongListViewModel(),
    modifier: Modifier = Modifier,
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_keyboard_backspace_24),
                contentDescription = null
            )
            Text(
                text = "歌单广场",
                modifier = modifier.padding(start = 10.dp),
                fontWeight = FontWeight.Bold
            )
        }


        // TODO 歌单标签选择
        LazyRow(){

        }

    }
}


@Preview
@Composable
fun TopBarPreview() {
    TopBar()
}