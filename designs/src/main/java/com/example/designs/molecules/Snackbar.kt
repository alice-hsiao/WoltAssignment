package com.example.designs.molecules

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.designs.atoms.basic.Icon
import com.example.designs.atoms.basic.MyIcons
import com.example.designs.atoms.basic.Surface
import com.example.designs.atoms.basic.Text
import com.example.designs.theme.DesignSystemTheme

@Composable
fun Snackbar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(4.dp),
        shape = DesignSystemTheme.shapes.small,
        color = DesignSystemTheme.colorsScheme.error4
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(painter = MyIcons.favorite, tint = DesignSystemTheme.colorsScheme.shadesWhite)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Something went wrong",
                modifier = Modifier.weight(1f),
                color = DesignSystemTheme.colorsScheme.shadesWhite,
                textStyle = DesignSystemTheme.typography.h5.regular
            )
        }
    }
}