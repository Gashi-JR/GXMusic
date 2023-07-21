package com.example.viewmodellist.ui.components


import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.screens.find.FindviewModel

data class SongItem(val imageUrl: String, val title: String, val playCount: Long)

@Composable
fun FindCard(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxWidth()

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = title),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                    contentDescription = stringResource(
                        id = title
                    ),
                    tint = Color.Black.copy(alpha = 0.5f)
                )
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_more_vert_24),
                    contentDescription = stringResource(
                        id = title
                    ),
                    tint = Color.Black.copy(alpha = 0.5f)
                )
            }

        }
        content()

        Spacer(modifier = Modifier.height(30.dp))
        Divider(thickness = 0.2.dp, color = Color.Gray.copy(alpha = 0.3f))
    }
}


@Preview
@Composable
fun FindCardPreview() {

    FindCard(R.string.find_recommendsonglist) {
        LazyRow(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items(FindviewModel().songItems) { item ->
                Spacer(modifier = Modifier.width(5.dp)) // 添加左侧边距
                SonglistCover(
                    imageUrl = item.imageUrl,
                    title = item.title,
                    playCount = item.playCount,
                )
                Spacer(modifier = Modifier.width(5.dp)) // 添加右侧边距
            }
        }
    }
}