package com.example.designs.atoms.basic

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designs.theme.DesignSystemTheme
import com.example.designs.theme.LocalMyContentColor
import com.example.designs.theme.contentColor

@Composable
fun Surface(
    modifier: Modifier = Modifier,
    color: Color = DesignSystemTheme.colorsScheme.shadesWhite,
    contentColor: Color = color.contentColor(),
    shape: Shape = RectangleShape,
    borderStroke: BorderStroke? = null,
    elevation: Dp = 0.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    CompositionLocalProvider(
        LocalMyContentColor provides contentColor
    ) {
        Box(
            modifier = modifier
                .shadow(
                    elevation = elevation,
                    shape = DesignSystemTheme.shapes.medium
                )
                .clip(shape)
                .background(color = color, shape = shape)
                .clickable(enabled = onClick != null, onClick = onClick ?: {})
                .then(
                    borderStroke?.let { Modifier.border(it, shape = shape) } ?: Modifier
                )
        ) {
            content()
        }
    }
}