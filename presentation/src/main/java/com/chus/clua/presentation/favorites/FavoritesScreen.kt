package com.chus.clua.presentation.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chus.clua.presentation.R
import com.chus.clua.presentation.model.MovieList
import com.chus.clua.presentation.movies.MovieItemList

@Composable
fun FavoritesScreenRoute(
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {

    val viewModel: FavoriteMoviesViewModel = hiltViewModel()
    val movies = viewModel.moviesFlow.collectAsStateWithLifecycle()

    FavoritesScreen(
        movies = movies.value,
        onMovieClick = onMovieClick,
        paddingValues = paddingValues
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreen(
    movies: List<MovieList>,
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
            }
        )

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
}