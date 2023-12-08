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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.bumptech.glide.integration.compose.placeholder
import com.chus.clua.presentation.R
import com.chus.clua.presentation.compose.Movie
import com.chus.clua.presentation.compose.composables.AppAlertDialog
import com.chus.clua.presentation.compose.composables.AppEmptyScreen
import com.chus.clua.presentation.compose.composables.AppTopBar
import com.chus.clua.presentation.model.MovieList

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
private fun FavoritesScreen(
    movies: List<MovieList>,
    onClearAllClicked: () -> Unit,
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {

    val openAlertDialog = remember { mutableStateOf(false) }

    if (openAlertDialog.value) {
        AppAlertDialog(
            icon = Icons.Default.Info,
            title = stringResource(id = R.string.favorites_delete_all_title),
            message = stringResource(id = R.string.favorites_delete_all_message),
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
                onClearAllClicked()
            }
        )
    }

    Column {
        AppTopBar(
            title = stringResource(id = R.string.favorites),
            actions = {
                IconButton(onClick = { openAlertDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                    )
                }
            }
        )
        if (movies.isEmpty()) {
            AppEmptyScreen(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Favorite,
                message = stringResource(id = R.string.favorites_empty_list_message) ,
                paddingValues = paddingValues
            )
        } else {
            FavoriteList(
                movies = movies,
                onMovieClick = onMovieClick,
                paddingValues = paddingValues
            )
        }
    }

}

@Composable
private fun FavoriteList(
    movies: List<MovieList>,
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues,
) {

    LazyColumn(
        modifier = Modifier
            .padding(paddingValues),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies.size) { index ->
            MovieItemList(movie = movies[index], onMovieClick = onMovieClick)
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItemList(
    movie: MovieList,
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
            model = movie.backdropUrl,
            contentScale = ContentScale.Crop,
            contentDescription = "MoviePoster",
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center),
            failure = placeholder(R.drawable.ic_tmdb)
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
                contentDescription = "Rate",
                tint = Color.White,
            )
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Text(
                text = String.format("%.1f", movie.voteAverage),
                color = Color.White,
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
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = movie.title,
                color = Color.White,
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