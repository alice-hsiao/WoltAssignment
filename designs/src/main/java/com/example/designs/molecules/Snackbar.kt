package com.example.designs.molecules

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.designs.atoms.basic.Surface
import com.example.designs.theme.DesignSystemTheme

private val snackbarHeight = 64.dp

@Composable
fun Snackbar(
    modifier: Modifier = Modifier,
    color: Color = DesignSystemTheme.colorsScheme.shadesWhite,
    trailingIcon: @Composable () -> Unit = {},
    content: @Composable RowScope.() -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(snackbarHeight)
            .padding(4.dp),
        shape = DesignSystemTheme.shapes.small,
        color = color,
        borderStroke = BorderStroke(2.dp, DesignSystemTheme.colorsScheme.neutral3)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            trailingIcon()
            Spacer(modifier = Modifier.width(8.dp))
            content()
        }
    }
}