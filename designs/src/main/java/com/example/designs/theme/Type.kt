package com.example.designs.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.designs.R

internal val LocalMyType = staticCompositionLocalOf<MyTypography> {
    error("LocalMyType is not set yet!")
}

private val PT_SANS = FontFamily(
    Font(R.font.pt_sans, FontWeight.Normal), Font(R.font.pt_sans_bold, FontWeight.Bold)
)

private val defaultTextStyle = TextStyle(
    fontFamily = PT_SANS
)

private val d1 = object : Style {
    override val regular: TextStyle
        get() = defaultTextStyle.copy(
            fontWeight = FontWeight(400),
            fontSize = 96.sp,
            lineHeight = 120.sp
        )
    override val bold: TextStyle
        get() = defaultTextStyle.copy(
            fontWeight = FontWeight(700),
            fontSize = 96.sp,
            lineHeight = 120.sp
        )
}

private val h4 = object : Style {
    override val regular: TextStyle
        get() = defaultTextStyle.copy(
            fontWeight = FontWeight(400),
            fontSize = 18.sp,
            lineHeight = 24.sp
        )
    override val bold: TextStyle
        get() = defaultTextStyle.copy(
            fontWeight = FontWeight(700),
            fontSize = 18.sp,
            lineHeight = 24.sp
        )
}

private val h5 = object : Style {
    override val regular: TextStyle
        get() = defaultTextStyle.copy(
            fontWeight = FontWeight(400),
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    override val bold: TextStyle
        get() = defaultTextStyle.copy(
            fontWeight = FontWeight(700),
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
}


internal val typography = MyTypography(
    default = defaultTextStyle,
    d1 = d1,
    h4 = h4,
    h5 = h5
)

interface Style {
    val regular: TextStyle
    val bold: TextStyle
}

@Stable
data class MyTypography(
    val default: TextStyle,
    val d1: Style,
    val h4: Style,
    val h5: Style,
)
