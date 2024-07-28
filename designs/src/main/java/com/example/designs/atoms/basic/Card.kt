package com.example.designs.atoms.basic

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designs.theme.DesignSystemTheme

@Composable
fun Card(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Surface(
        modifier = modifier,
        shape = DesignSystemTheme.shapes.medium,
        onClick = onClick,
        elevation = 2.dp,
        content = content
    )
}
