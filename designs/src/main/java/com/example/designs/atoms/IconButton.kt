package com.example.designs.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.designs.theme.DesignSystemTheme
import com.example.designs.theme.LocalMyContentColor
import com.example.designs.theme.contentColor

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DesignSystemTheme.shapes.large,
    size: DpSize = DpSize(24.dp, 24.dp),
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .clickable(
                onClick = onClick,
                enabled = enabled,
                role = Role.Button,
            ),
        contentAlignment = Alignment.Center
    ) {
        val contentColor = DesignSystemTheme.colorsScheme.neutral3.contentColor()
        CompositionLocalProvider(
            LocalMyContentColor provides contentColor,
            content = content
        )
    }
}