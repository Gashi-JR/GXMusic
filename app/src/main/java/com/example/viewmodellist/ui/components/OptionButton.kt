package com.example.viewmodellist.ui.components

import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.R

@Composable
fun OptionButton(modifier: Modifier = Modifier) {
    IconButton(onClick = { /*TODO*/ }, modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_dehaze_24),
            contentDescription = "option"
        )
    }

}


@Preview
@Composable
fun OptionbuttonPreview() {
    OptionButton()
}