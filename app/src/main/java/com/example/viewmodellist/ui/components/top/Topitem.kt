package com.example.viewmodellist.ui.components.top


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter


@Composable
fun Topitem(
    updatetime: String = "",
    topimg: String = "",
    onClick: () -> Unit = {},
) {

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Color(0, 0, 0, 0)

        ),
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(0.3f)
            .aspectRatio(1f)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(topimg),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )
            Text(
                text = updatetime,
                fontSize = 11.sp,
                color = Color.White.copy(0.5f),
                modifier = Modifier
                    .align(BottomCenter)
            )
        }


    }
}


@Preview
@Composable
fun TopitemPreview() {

}

