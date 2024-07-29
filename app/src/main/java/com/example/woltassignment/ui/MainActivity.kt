package com.example.woltassignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.designs.molecules.Snackbar
import com.example.designs.theme.DesignSystemTheme
import com.example.woltassignment.MyApp
import com.example.woltassignment.domain.model.Restaurant
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DesignSystemTheme {
                val mainViewModel by viewModels<MainViewModel> {
                    MainViewModel.Factory(MyApp.appModule)
                }
                val state = mainViewModel.state.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(
                            hostState = snackbarHostState,
                            snackbar = { Snackbar() }
                        )
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) { innerPadding ->

                    MainContent(
                        restaurant = state.value.restaurants,
                        contentPadding = innerPadding,
                        onLikeClicked = mainViewModel::processAction
                    )
                }

                LaunchedEffect(Unit) {
                    mainViewModel.effect.collectLatest {
                        when (it) {
                            is MainViewModel.MainEffect.ShowSnackbar -> {
                                snackbarHostState.showSnackbar(message = it.error)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainContent(
    restaurant: ImmutableList<Restaurant>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(),
    onLikeClicked: (MainViewModel.MainAction.OnLikeClicked) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = restaurant, key = { it.id }) {
            RestaurantCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateItemPlacement(),
                name = it.name,
                url = it.url,
                description = it.description,
                liked = it.liked,
                onLikeClicked = { liked ->
                    onLikeClicked(MainViewModel.MainAction.OnLikeClicked(it.id, liked))
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
