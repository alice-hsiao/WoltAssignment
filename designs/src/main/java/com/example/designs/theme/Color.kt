package com.example.designs.theme

import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val Neutral1 = Color(0xFFF2F2F2)
private val Neutral2 = Color(0xFFE6E6E6)
private val Neutral3 = Color(0xFFCCCCCC)
private val Neutral7 = Color(0xFF666666)
private val Neutral10 = Color(0xFF1A1A1A)

private val Primary1 = Color(0xFFEBEBFA)
private val Primary2 = Color(0xFFADADEB)
private val Primary4 = Color(0xFF3333CC)

private val Success4 = Color(0xFF33CC79)

private val Error4 = Color(0xFFDF535F)

private val ShadesWhite = Color.White
private val ShadesBlack = Color.Black

@Stable
data class MyColors(
    val neutral1: Color,
    val neutral2: Color,
    val neutral3: Color,
    val neutral7: Color,
    val neutral10: Color,
    val primary1: Color,
    val primary2: Color,
    val primary4: Color,
    val success4: Color,
    val error4: Color,
    val shadesWhite: Color,
    val shadesBlack: Color,
)

internal val LocalMyColors = staticCompositionLocalOf<MyColors> {
    error("LocalMyColors not set yet!")
}

val LocalMyContentColor = compositionLocalOf {
    Neutral10
}

fun Color.contentColor() = when (this) {
    Primary2, Primary4 -> ShadesWhite
    ShadesBlack -> ShadesWhite
    ShadesWhite -> ShadesBlack
    else -> ShadesBlack
}

internal val lightColors = MyColors(
    neutral1 = Neutral1,
    neutral2 = Neutral2,
    neutral3 = Neutral3,
    neutral7 = Neutral7,
    neutral10 = Neutral10,
    primary1 = Primary1,
    primary2 = Primary2,
    primary4 = Primary4,
    success4 = Success4,
    error4 = Error4,
    shadesWhite = ShadesWhite,
    shadesBlack = ShadesBlack,
)

internal val darkColors = MyColors(
    neutral1 = Neutral1,
    neutral2 = Neutral2,
    neutral3 = Neutral3,
    neutral7 = Neutral7,
    neutral10 = Neutral10,
    primary1 = Primary1,
    primary2 = Primary2,
    primary4 = Primary4,
    success4 = Success4,
    error4 = Error4,
    shadesWhite = ShadesWhite,
    shadesBlack = ShadesBlack,
)