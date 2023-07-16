package com.example.viewmodellist.ui.screens.find

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.R
import com.example.viewmodellist.ui.components.OptionButton
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Find(listviewModel: DataviewModel, modifier: Modifier = Modifier) {
    var inputtext by rememberSaveable {
        mutableStateOf("")
    }

    Column() {
        Card() {
            Row(modifier = modifier.background(Color.White)) {
                OptionButton(modifier = Modifier.height(50.dp))
                TextField(
                    value = inputtext,
                    onValueChange = { inputtext = it },
                    modifier = Modifier
                        .weight(1F)
                        .height(40.dp)
                        .padding(top = 10.dp)
                        .clip(MaterialTheme.shapes.medium),
                    singleLine = true,
                    maxLines = 1,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_search_24),
                            contentDescription = "search"
                        )
                    },
                    trailingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_clear_24),
                            contentDescription = "clear"
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent // 取消下划线
                    ),
                )
                Spacer(modifier = Modifier.width(30.dp))
            }

        }


    }


}

@Preview(showBackground = true)
@Composable
fun FindPreview() {
    ViewModelListTheme {
        Find(DataviewModel())
    }
}