package com.example.designs.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun DesignSystemTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColors
        else -> lightColors
    }

    CompositionLocalProvider(
        LocalMyColors provides colorScheme,
        LocalMyType provides typography,
        LocalMyShapes provides defaultShapes
    ) {
        content()
    }
}

object DesignSystemTheme {
    val colorsScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalMyColors.current

    val typography
        @Composable
        @ReadOnlyComposable
        get() = LocalMyType.current
    
    val shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalMyShapes.current
}