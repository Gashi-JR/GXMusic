package com.example.viewmodellist.ui.screens.top

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@Composable
fun Top() {
    Text(text = "top")
}


@Preview
@Composable
fun TopPreview() {
    ViewModelListTheme {
        Top()
    }
}