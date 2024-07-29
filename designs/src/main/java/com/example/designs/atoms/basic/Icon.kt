package com.example.designs.atoms.basic

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toolingGraphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designs.R
import com.example.designs.theme.LocalMyContentColor

@Composable
fun Icon(
    painter: Painter,
    modifier: Modifier = Modifier,
    tint: Color = LocalMyContentColor.current,
    size: Dp = 24.dp
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .toolingGraphicsLayer()
            .paint(
                painter = painter,
                colorFilter = ColorFilter.tint(color = tint),
                contentScale = ContentScale.Fit
            )
            .size(size)
    )
}

object MyIcons {
    val favorite: Painter
        @Composable
        get() = painterResource(id = R.drawable.favorite)

    val favorite_border: Painter
        @Composable
        get() = painterResource(id = R.drawable.favorite_border)
    val error: Painter
        @Composable
        get() = painterResource(id = R.drawable.error)

}