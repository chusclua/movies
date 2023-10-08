package com.chus.clua.presentation.favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.model.FavoriteMovieList

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
    movies: List<FavoriteMovieList>,
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
    movies: List<FavoriteMovieList>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(paddingValues)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
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
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
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

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItemList(
    movie: FavoriteMovieList,
    onMovieClick: (movieId: Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onMovieClick(movie.id)
            }
    ) {
        GlideImage(
            model = movie.backdropPath,
            contentScale = ContentScale.Crop,
            contentDescription = "MoviePoster",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        )
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Rate"
            )
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Text(
                text = String.format("%.1f", movie.voteAverage),
                style = MaterialTheme.typography.titleSmall
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = movie.year,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.headlineSmall.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.7F),
                        offset = Offset(5.0f, 10.0f),
                        blurRadius = 3f
                    )
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMovieItemList() {
    MovieItemList(
        movie = Movie,
        onMovieClick = {}
    )
}

@Preview
@Composable
private fun PreviewEmptyFavoriteList() {
    EmptyFavoriteList(PaddingValues())
}

private val Movie = FavoriteMovieList(
    id = 238,
    title = "The Godfather Part II",
    backdropPath = "https://image.tmdb.org/t/p/w342/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    year = "1972",
    voteAverage = 8.7,
)