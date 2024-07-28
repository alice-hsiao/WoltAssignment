package com.example.designs.atoms.basic

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.TextStyle
import com.example.designs.theme.DesignSystemTheme
import com.example.designs.theme.LocalMyContentColor

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    textStyle: TextStyle = DesignSystemTheme.typography.h4.bold,
) {
    val textColor = color.takeOrElse {
        textStyle.color.takeOrElse {
            LocalMyContentColor.current
        }
    }

    val mergedTextStyle = textStyle.copy(color = textColor)

    BasicText(
        text = text,
        modifier = modifier,
        style = mergedTextStyle
    )
}