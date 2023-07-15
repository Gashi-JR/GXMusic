package com.example.viewmodellist.ui.screens.mine

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@Composable
fun Mine() {
    Text(text = "mine")
}


@Preview
@Composable
fun MinePreview() {
    ViewModelListTheme {
        Mine()
    }
}