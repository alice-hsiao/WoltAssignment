package com.example.designs.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

internal val LocalMyShapes = staticCompositionLocalOf<MyShapes> {
    error("MyShapes not set yet!")
}

@Stable
data class MyShapes(
    val small: RoundedCornerShape,
    val medium: RoundedCornerShape,
    val large: RoundedCornerShape
)

internal val defaultShapes = MyShapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(20.dp),
    large = RoundedCornerShape(60.dp)
)