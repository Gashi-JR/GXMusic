package com.example.viewmodellist.ui.screens.login

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import com.example.viewmodellist.ui.theme.ViewModelListTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(modifier: Modifier = Modifier) {
    TextField(value = "Login", onValueChange = {}, modifier = modifier)
}

@Preview
@Composable
fun LoginPreview() {
    ViewModelListTheme {
        Login()
    }

}