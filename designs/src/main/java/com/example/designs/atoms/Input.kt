package com.example.designs.atoms

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.unit.dp
import com.example.designs.atoms.basic.Surface
import com.example.designs.theme.DesignSystemTheme
import com.example.designs.theme.LocalMyContentColor

enum class InputType {
    Default,
    Success,
    Danger
}

@Composable
fun rememberInputTypeState(type: InputType = InputType.Default): InputTypeState {
    return rememberSaveable(saver = InputTypeState.Saver) {
        InputTypeState(type)
    }
}

class InputTypeState(private val inputType: InputType) {
    var value by mutableStateOf(inputType)
        private set


    fun updateType(type: InputType) {
        value = type
    }

    companion object {
        val Saver: Saver<InputTypeState, *> = listSaver(
            save = { listOf(it.value) },
            restore = { list -> InputTypeState(list[0]) }
        )
    }
}

@Composable
fun Input(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    inputTypeState: InputTypeState = rememberInputTypeState(),
    shape: Shape = DesignSystemTheme.shapes.small,
    onTextChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var inputText by rememberSaveable {
        mutableStateOf(text)
    }

    var isFocused by rememberSaveable {
        mutableStateOf(false)
    }

    val strokeWidth = when {
        isFocused ||
                inputTypeState.value == InputType.Success ||
                inputTypeState.value == InputType.Danger -> 2.dp

        else -> 1.dp
    }

    ProvideInputSpecs(
        enabled = enabled,
        inputTypeState = inputTypeState.value,
        isFocused = isFocused,
    ) { highlightColor, backgroundColor, contentColor, borderColor ->
        Surface(
            modifier = modifier
                .border(
                    width = strokeWidth,
                    color = borderColor,
                    shape = shape
                )
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChange(it.isFocused)
                    if (it.isFocused && inputText == text) {
                        inputText = ""
                    }
                },
            shape = shape,
            color = backgroundColor,
            contentColor = contentColor,
        ) {
            BasicTextField(
                value = inputText,
                onValueChange = {
                    inputText = it
                    onTextChange(it)
                    inputTypeState.updateType(InputType.Default)
                },
                enabled = enabled,
                textStyle = DesignSystemTheme.typography.h4.regular.copy(color = contentColor),
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        if (leadingIcon != null) {
                            leadingIcon()
                            Spacer(Modifier.width(8.dp))
                        }

                        Box(modifier = Modifier.weight(1f)) {
                            innerTextField()
                        }

                        if (trailingIcon != null) {
                            Spacer(Modifier.width(8.dp))
                            CompositionLocalProvider(LocalMyContentColor provides highlightColor) {
                                trailingIcon()
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun ProvideInputSpecs(
    enabled: Boolean,
    inputTypeState: InputType,
    isFocused: Boolean,
    specs: @Composable (Color, Color, Color, Color) -> Unit
) {
    val (highlightColor, backgroundColor, contentColor, borderColor) =
        with(DesignSystemTheme.colorsScheme) {
            when {
                !enabled -> listOf(
                    shadesBlack.copy(alpha = .5f).compositeOver(Color.White),
                    neutral1.copy(alpha = .5f).compositeOver(Color.White),
                    shadesBlack.copy(alpha = .5f).compositeOver(Color.White),
                    neutral3.copy(alpha = .5f).compositeOver(Color.White),
                )

                inputTypeState == InputType.Danger -> listOf(
                    error4,
                    neutral1,
                    shadesBlack,
                    error4,
                )

                isFocused -> listOf(
                    primary4,
                    neutral1,
                    shadesBlack,
                    primary4,
                )

                inputTypeState == InputType.Success -> listOf(
                    success4,
                    neutral1,
                    shadesBlack,
                    success4,
                )

                else -> listOf(
                    shadesBlack,
                    neutral1,
                    shadesBlack,
                    neutral3
                )
            }
        }
    specs(highlightColor, backgroundColor, contentColor, borderColor)
}
