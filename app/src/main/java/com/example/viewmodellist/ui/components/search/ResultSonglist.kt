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
import com.example.viewmodellist.utils.Datamodels
import com.example.viewmodellist.utils.formatter
import java.util.Date


@Composable
fun ResultSonglist(
    songlist: Datamodels.ResultSonglist,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .clip(shape = MaterialTheme.shapes.small)

    ) {

        Card(
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
        ) {

            Image(
                painter = rememberAsyncImagePainter(songlist.coverImgUrl),
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .width(250.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row() {
                    Text(
                        text = songlist.name,
                        fontSize = 16.sp,
                        maxLines = 1,
                        modifier = Modifier.width(120.dp)
                    )
                    songlist.officialTags?.forEach { item ->
                        Tag(onClick = { /*TODO*/ }, modifier = Modifier.height(14.dp)) {
                            Text(
                                text = item,
                                color = Color.Red,
                                fontSize = 10.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "${songlist.trackCount}首 , by ${songlist.creater} , 播放${
                        formatter.tranNumber(
                            songlist.playCount, 0
                        )
                    }次",
                    fontSize = 12.sp,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    modifier = Modifier.width(250.dp),
                    color = Color.Gray
                )
            }


        }

    }
    Spacer(modifier = Modifier.width(25.dp))
    Divider(
        modifier = Modifier.padding(horizontal = 15.dp),
        thickness = 0.15.dp,
        color = Color.Gray.copy(0.2f)
    )
}


@Preview(showBackground = true)
@Composable
fun ResultSonglistPreview() {
    ResultSonglist(
        Datamodels.ResultSonglist(1, 1, 1, ",", "", "", listOf())
    )
}



