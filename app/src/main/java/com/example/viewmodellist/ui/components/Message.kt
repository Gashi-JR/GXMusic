package com.example.viewmodellist.ui.components


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.viewmodellist.R
import kotlin.math.roundToInt

@Composable
fun Message(
    message: String = "",
    type: Int = 0,
    show: Boolean = true,
    @SuppressLint("ModifierParameter")
    modifier: Modifier = Modifier

) {
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically(
            initialOffsetY = { -2 * it },
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = slideOutVertically(targetOffsetY = { -2 * it })
    ) {
        FloatingActionButton(
            onClick = {},
            modifier = modifier
                .width(170.dp)
                .height(45.dp)
                .offset { IntOffset(420f.roundToInt(), 50f.roundToInt()) },
            shape = MaterialTheme.shapes.extraLarge,
            contentColor = Color.White,
            containerColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 2.dp
            )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {
                when (type) {
                    0 -> Icon(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(25.dp)
                    )

                    1 -> Icon(
                        painter = painterResource(id = R.drawable.warning),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(25.dp)
                    )

                    2 -> Icon(
                        painter = painterResource(id = R.drawable.error),
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = message,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    Message()
}

