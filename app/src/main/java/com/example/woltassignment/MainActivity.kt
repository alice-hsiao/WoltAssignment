package com.example.woltassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.compose.AsyncImage
import com.example.woltassignment.domain.model.Restaurant
import com.example.woltassignment.ui.theme.WoltAssignmentTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoltAssignmentTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) { innerPadding ->
                    val mainViewModel by viewModels<MainViewModel> {
                        MainViewModel.Factory
                    }

                    val state = mainViewModel.state.collectAsState()
                    MainContent(restaurant = state.value.restaurants,
                        contentPadding = innerPadding,
                        onLikeClicked = { id, liked ->
                            mainViewModel.processAction(
                                MainViewModel.MainAction.OnLikeClicked(
                                    id = id, isLiked = liked
                                )
                            )
                        })
                }
            }
        }
    }
}

@Composable
fun MainContent(
    restaurant: ImmutableList<Restaurant>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onLikeClicked: (id: String, liked: Boolean) -> Unit = { id, liked -> }
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = restaurant, key = { it.id }) {
            DetailedCard(
                name = it.name,
                url = it.url,
                description = it.description,
                liked = it.liked,
                modifier = Modifier.fillMaxWidth(),
                onLikeClicked = { liked -> onLikeClicked(it.id, liked) })
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider()
        }
    }
}

@Composable
fun DetailedCard(
    name: String,
    url: String,
    description: String,
    liked: Boolean,
    modifier: Modifier = Modifier,
    onLikeClicked: (Boolean) -> Unit = {}
) {
    Card(modifier = modifier, onClick = {}) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = url,
                contentDescription = "Restaurant Display Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = name)
                Text(text = description)
            }

            IconToggleButton(checked = liked, onCheckedChange = { onLikeClicked(it) }) {
                Icon(imageVector = Icons.Filled.Star, contentDescription = "like")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    WoltAssignmentTheme {
        MainContent(
            restaurant = persistentListOf(
                Restaurant("123", "Mc", "fries", "url", true),
                Restaurant("223", "KFC", "chicken", "url2", false)
            )
        )
    }
}