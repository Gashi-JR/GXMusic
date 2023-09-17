package com.example.viewmodellist.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.R

@Composable
fun OptionButton(modifier: Modifier = Modifier) {
    IconButton(onClick = { /*TODO*/ }, modifier = modifier) {
        Icon(
            painter = painterResource(id = R.drawable.gxlogo),
            contentDescription = "option",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified
        )
    }

}


@Preview
@Composable
fun OptionbuttonPreview() {
    OptionButton()
}