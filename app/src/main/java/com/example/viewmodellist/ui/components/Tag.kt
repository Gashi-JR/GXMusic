package com.example.viewmodellist.ui.components

import android.annotation.SuppressLint
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Tag(
    onClick: () -> Unit, backgroundColor: Color = Color(
        247,
        231,
        232
    ), @SuppressLint("ModifierParameter") modifier: Modifier = Modifier, content: @Composable () -> Unit
) {
    Chip(
        onClick = onClick,
        colors = ChipDefaults.chipColors(
            backgroundColor = backgroundColor
        ),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
    ) {
        content()
    }
}
