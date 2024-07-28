package com.example.designs.molecules

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.designs.atoms.Input
import com.example.designs.atoms.InputType
import com.example.designs.atoms.InputTypeState
import com.example.designs.atoms.basic.Text
import com.example.designs.atoms.rememberInputTypeState
import com.example.designs.theme.DesignSystemTheme


@Composable
fun InputGroup(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    inputType: InputTypeState = rememberInputTypeState(),
    shape: Shape = DesignSystemTheme.shapes.small,
    title: String = "",
    hint: String = "",
    onTextChange: (String) -> Unit = {},
    onFocusChange: (Boolean) -> Unit = {},
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val hintColor = if (inputType.value == InputType.Danger) {
        DesignSystemTheme.colorsScheme.error4
    } else {
        DesignSystemTheme.colorsScheme.neutral7
    }

    Column(modifier) {
        Text(text = title, textStyle = DesignSystemTheme.typography.h4.regular)
        Spacer(modifier = Modifier.height(8.dp))
        Input(
            text = text,
            enabled = enabled,
            inputTypeState = inputType,
            shape = shape,
            onTextChange = onTextChange,
            onFocusChange = onFocusChange,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = hint,
            textStyle = DesignSystemTheme.typography.h5.regular,
            color = hintColor
        )
    }
}