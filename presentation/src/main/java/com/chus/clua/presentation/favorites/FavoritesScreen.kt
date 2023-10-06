package com.chus.clua.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chus.clua.presentation.R
import com.chus.clua.presentation.model.MovieList
import com.chus.clua.presentation.movies.MovieItemList

@Composable
fun FavoritesScreenRoute(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {

    val movies = viewModel.moviesFlow.collectAsStateWithLifecycle()

    FavoritesScreen(
        movies = movies.value.movies,
        onClearAllClicked = viewModel::onClearAllClicked,
        onMovieClick = onMovieClick,
        paddingValues = paddingValues
    )

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun FavoritesScreen(
    movies: List<MovieList>,
    onClearAllClicked: () -> Unit,
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {

    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
                actionIconContentColor = MaterialTheme.colorScheme.primary
            ),
            title = {
                Text(
                    text = stringResource(id = R.string.favorites),
                    style = MaterialTheme.typography.titleLarge
                )
            },
            actions = {
                IconButton(onClick = onClearAllClicked) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                    )
                }
            }
        )
        if (movies.isEmpty()) {
            EmptyFavoriteList(paddingValues)
        } else {
            FavoriteList(paddingValues, movies, onMovieClick)
        }
    }

}

@Composable
private fun FavoriteList(
    paddingValues: PaddingValues,
    movies: List<MovieList>,
    onMovieClick: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(
            start = 12.dp,
            top = 12.dp,
            end = 12.dp,
            bottom = 12.dp
        )
    ) {
        items(movies.size) { index ->
            MovieItemList(movie = movies[index], onMovieClick = onMovieClick)
        }
    }
}

@Composable
private fun EmptyFavoriteList(
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = Icons.Filled.Favorite,
            contentDescription = "Favorites",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = stringResource(id = R.string.favorites_empty_list_message),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
@Preview
private fun PreviewEmptyFavoriteList() {
    EmptyFavoriteList(PaddingValues())
}