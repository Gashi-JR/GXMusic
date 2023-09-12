package com.example.viewmodellist.ui.components.search


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.Tag
import com.example.viewmodellist.ui.components.songlist.SongItem
import com.example.viewmodellist.utils.Datamodels
import com.example.viewmodellist.utils.formatter
import java.util.Date


@Composable
fun ResultSong(
    index: Int,
    song: Datamodels.ResultSong,
    isSelected: Boolean = false,

    modifier: Modifier = Modifier
) {
//    Row(
//        modifier = modifier
//            .height(60.dp)
//            .clip(shape = MaterialTheme.shapes.small)
//
//    ) {
//
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween,
//            modifier = Modifier
//                .fillMaxWidth()
//
//        ) {
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(start = 10.dp),
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text(
//                    text = song.name,
//                    fontSize = 16.sp,
//                    maxLines = 1,
//                    modifier = Modifier.width(120.dp)
//                )
//                Spacer(modifier = Modifier.height(5.dp))
//                Row() {
//                    Tag(onClick = { /*TODO*/ }, modifier = Modifier.height(14.dp)) {
//                        Text(
//                            text = formatter.convertTimestampToDateString(song.publishTime),
//                            color = Color.Red,
//                            fontSize = 10.sp
//                        )
//                    }
//                    Text(
//                        text = song.artist + "-" + song.al,
//                        fontSize = 12.sp,
//                        overflow = TextOverflow.Ellipsis,
//                        maxLines = 2,
//                        modifier = Modifier.width(150.dp),
//                        color = Color.Gray
//                    )
//                }
//
//            }
//            Row() {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_smart_display_24),
//                    contentDescription = null,
//                    tint = Color.Black.copy(alpha = 0.2f),
//
//                    )
//                Spacer(modifier = Modifier.width(25.dp))
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
//                    contentDescription = null,
//                    tint = Color.Black.copy(alpha = 0.2f),
//
//                    )
//                Spacer(modifier = Modifier.width(25.dp))
//            }
//
//        }
//
//    }
//    Spacer(modifier = Modifier.width(25.dp))

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

