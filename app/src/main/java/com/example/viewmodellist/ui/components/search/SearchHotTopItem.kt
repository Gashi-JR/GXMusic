package com.example.viewmodellist.ui.components.search


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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.Tag


@Composable
fun SearchHotTopItem(
    searchWord: String,
    score: Long,
    content: String,
    iconUrl: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(60.dp)
            .clip(shape = MaterialTheme.shapes.small)

    ) {

        Box {
            Tag(
                onClick = {},
                modifier = Modifier
                    .height(20.dp)
                    .padding(bottom = 5.dp)
                    .align(Alignment.TopCenter)
            ) {
                Text(
                    text = "热度值:$score",
                    color = Color(250, 65, 64),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }



            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(start = 10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = searchWord,
                        fontSize = 16.sp,
                        maxLines = 1,
                        modifier = Modifier.width(120.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = content,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        modifier = Modifier.width(250.dp),
                        color = Color.Gray
                    )
                }
                if (!iconUrl.isNullOrBlank())
                    Icon(
                        painter = painterResource(id = R.drawable.h),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(18.dp)
                    )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun SearchHotTopItemPreview() {
    SearchHotTopItem(
        "1313",
        1412,
        "64412",
        "adadadadw",
    )
}