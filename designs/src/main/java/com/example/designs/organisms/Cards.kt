package com.example.designs.organisms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designs.atoms.basic.Card

@Composable
fun DetailedCard(
    modifier: Modifier = Modifier,
    mainContentHeight: Dp = 150.dp,
    mainContent: @Composable () -> Unit,
    subContent: @Composable ColumnScope.() -> Unit,
    onMainContent: @Composable BoxScope.() -> Unit = {},
) {
    Card(modifier = modifier) {
        Column {
            Box(modifier = Modifier.height(mainContentHeight)) {
                mainContent()
                onMainContent()
            }
            Spacer(modifier = Modifier.height(16.dp))
            subContent()
        }
    }
}
