package com.example.woltassignment.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.designs.atoms.IconButton
import com.example.designs.atoms.basic.Icon
import com.example.designs.atoms.basic.MyIcons
import com.example.designs.atoms.basic.Text
import com.example.designs.molecules.Snackbar
import com.example.designs.organisms.DetailedCard
import com.example.designs.theme.DesignSystemTheme
import com.example.designs.theme.LocalMyContentColor

private val BIG_HEART_SIZE = 38.dp
private val SMALL_HEART_SIZE = 36.dp
private val IMAGE_HEIGHT = 150.dp

@Composable
fun RestaurantCard(
    name: String,
    url: String,
    description: String,
    liked: Boolean,
    modifier: Modifier = Modifier,
    onLikeClicked: (Boolean) -> Unit = {}
) {
    val painter = rememberAsyncImagePainter(model = url)
    val (icon, iconSize) = if (liked) {
        Pair(MyIcons.favorite, BIG_HEART_SIZE)
    } else {
        Pair(MyIcons.favorite_border, SMALL_HEART_SIZE)
    }

    DetailedCard(
        modifier = modifier,
        mainContent = {
            Image(
                painter = painter,
                contentDescription = "restaurant photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(IMAGE_HEIGHT)
                    .fillMaxWidth()
            )
        },
        subContent = {
            Text(
                text = name,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(36.dp),
                textStyle = DesignSystemTheme.typography.h5.bold
            )
            Text(
                text = description,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(36.dp),
                textStyle = DesignSystemTheme.typography.h5.regular,
                color = DesignSystemTheme.colorsScheme.neutral7
            )
            Spacer(modifier = Modifier.height(16.dp))
        },
        onMainContent = {
            IconButton(
                onClick = {
                    onLikeClicked(!liked)
                },
                size = DpSize(BIG_HEART_SIZE, BIG_HEART_SIZE),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = icon,
                    tint = DesignSystemTheme.colorsScheme.shadesWhite,
                    size = iconSize,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .animateContentSize()
                )
            }
        }
    )
}

@Composable
fun ErrorSnackbar(error: String, modifier: Modifier = Modifier) {
    Snackbar(
        modifier = modifier,
        color = DesignSystemTheme.colorsScheme.error4.copy(0.6f)
            .compositeOver(DesignSystemTheme.colorsScheme.shadesWhite),
        trailingIcon = {
            Icon(painter = MyIcons.error, tint = DesignSystemTheme.colorsScheme.error4)
        }
    ) {
        Text(
            text = error,
            modifier = Modifier.weight(1f),
            color = LocalMyContentColor.current,
            textStyle = DesignSystemTheme.typography.h5.bold
        )
    }
}