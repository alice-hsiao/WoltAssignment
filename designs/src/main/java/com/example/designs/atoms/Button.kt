package com.example.designs.atoms

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.designs.atoms.basic.Surface
import com.example.designs.theme.DesignSystemTheme
import com.example.designs.theme.contentColor

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = DesignSystemTheme.shapes.large,
    color: Color = DesignSystemTheme.colorsScheme.primary4,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit,
) {
    val (backgroundColor, contentColor) = with(color) {
        val contentColor = this.contentColor()
        if (enabled) {
            Pair(this, contentColor)
        } else {
            Pair(
                this.copy(alpha = 0.25f).compositeOver(Color.White),
                contentColor.copy(alpha = 0.25f).compositeOver(Color.White)
            )
        }
    }

    Surface(
        modifier = modifier
            .height(40.dp)
            .clip(shape)
            .shadow(elevation = 0.dp, shape = shape)
            .clickable(
                role = Role.Button,
                enabled = enabled,
                onClick = onClick
            ),
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.Center
        ) {
            if (leadingIcon != null) {
                leadingIcon()
            }

            content()

            if (trailingIcon != null) {
                trailingIcon()
            }
        }
    }
}
