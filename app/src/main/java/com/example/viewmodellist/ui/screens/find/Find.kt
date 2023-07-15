package com.example.viewmodellist.ui.screens.find

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Find(listviewModel: ListviewModel, modifier: Modifier = Modifier) {
    var inputtext by rememberSaveable {
        mutableStateOf("")
    }

    Column() {
        Card(elevation = CardDefaults.cardElevation()) {
            Row(modifier = modifier) {
                TextField(
                    value = inputtext,
                    onValueChange = { inputtext = it },
                    modifier = Modifier
                        .weight(1F)
                        .height(50.dp)
                        .clip(MaterialTheme.shapes.medium),
                    singleLine = true,
                    maxLines = 1,

                    )
                Button(onClick = { listviewModel.add(DataItem(inputtext)) }) {
                    Text(text = "保存")
                }
            }
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(
                items = listviewModel.listData
            ) { item: DataItem ->
                AnimatedVisibility(
                    visible = true,
                    enter = slideInHorizontally(
                        initialOffsetX = { fullWidth -> -fullWidth },
                        animationSpec = tween(durationMillis = 1000)
                    )
                ) {
                    Text(
                        text = item.text, modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Color.Cyan
                            )
                    )
                }
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelListTheme {
        Find(ListviewModel())
    }
}