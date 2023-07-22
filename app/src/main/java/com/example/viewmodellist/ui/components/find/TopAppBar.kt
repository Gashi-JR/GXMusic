package com.example.viewmodellist.ui.components.find


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.OptionButton
import com.example.viewmodellist.ui.theme.borderGradient


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
) {
    Row() {

        OptionButton(
            modifier = modifier
                .height(50.dp)
                .padding(top = 10.dp)
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0, 0, 0, 0)),
            modifier = Modifier
                .weight(1F)
                .height(50.dp)
                .padding(start = 10.dp, top = 10.dp, end = 10.dp)
                .border(0.3.dp, borderGradient, MaterialTheme.shapes.medium)
                .clip(MaterialTheme.shapes.medium),

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

                    Text(text = "大家都在搜 ", color = Color.Gray, fontSize = 14.sp)
                }

                androidx.compose.material.Icon(
                    painter = painterResource(id = R.drawable.baseline_clear_24),
                    contentDescription = "clear"
                )
            }


        }
        Spacer(modifier = Modifier.width(30.dp))
    }
}


@Preview
@Composable
fun TopAppBarPreview() {
    TopAppBar()
}