package com.chus.clua.presentation.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopEnd
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.compose.Movie
import com.chus.clua.presentation.compose.composables.AppTopBar
import com.chus.clua.presentation.compose.extensions.rememberLazyGridState
import com.chus.clua.presentation.model.MovieList

@Composable
fun MoviesScreenRoute(
    viewModel: MoviesViewModel = hiltViewModel(),
    onMovieClick: (movieId: Int) -> Unit,
    paddingValues: PaddingValues
) {

    val movies = viewModel.moviesFlow.collectAsLazyPagingItems()
    val topRatedMovies = viewModel.topRatedMoviesFlow.collectAsLazyPagingItems()

    MoviesScreen(
        movies = movies,
        topRatedMovies = topRatedMovies,
        onMovieClick = onMovieClick,
        paddingValues = paddingValues
    )

}

@Composable
private fun MoviesScreen(
    movies: LazyPagingItems<MovieList>,
    topRatedMovies: LazyPagingItems<MovieList>,
    onMovieClick: (movieId: Int) -> Unit,
    paddingValues: PaddingValues,
) {

    Column {

        AppTopBar(title = stringResource(id = R.string.movies))

        val listState = movies.rememberLazyGridState()

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(paddingValues),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = R.string.top_rated),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                val sidePadding = (-12).dp
                LazyRow(
                    modifier = Modifier.layout { measurable, constraints ->
                        // Measure the composable adding the side padding*2 (left+right)
                        val placeable =
                            measurable.measure(constraints.offset(horizontal = -sidePadding.roundToPx() * 2))

                        //increase the width adding the side padding*2
                        layout(
                            placeable.width + sidePadding.roundToPx() * 2,
                            placeable.height
                        ) {
                            // Where the composable gets placed
                            placeable.place(+sidePadding.roundToPx(), 0)
                        }

                    },
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp)
                ) {
                    items(topRatedMovies.itemCount) { index ->
                        topRatedMovies[index]?.let { movie ->
                            TopRatedItemList(movie = movie, onMovieClick = onMovieClick)
                        }
                    }
                }
            }
            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = stringResource(id = R.string.discover),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            items(movies.itemCount) { index ->
                movies[index]?.let { movie ->
                    DiscoverItemList(movie = movie, onMovieClick = onMovieClick)
                }
            }
        }
        if (movies.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(CenterHorizontally)
            )
        }

    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun TopRatedItemList(
    movie: MovieList,
    onMovieClick: (movieId: Int) -> Unit
) {
    Box(modifier = Modifier
        .size(width = 100.dp, height = 140.dp)
        .clip(RoundedCornerShape(8.dp))
        .clickable { onMovieClick(movie.id) }
    ) {
        GlideImage(
            model = movie.posterPath,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.align(Center)
        )
        Row(
            modifier = Modifier
                .align(TopEnd)
                .padding(top = 4.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Filled.Star,
                contentDescription = "Rate",
                tint = Color.White
            )
            Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            Text(
                text = String.format("%.1f", movie.voteAverage),
                color = Color.White,
                style = MaterialTheme.typography.titleSmall.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.7F),
                        offset = Offset(2.5F, 5.0F),
                        blurRadius = 3F
                    )
                )
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun DiscoverItemList(
    movie: MovieList,
    onMovieClick: (movieId: Int) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onMovieClick(movie.id)
        }
    ) {
        GlideImage(
            model = movie.posterPath,
            contentDescription = "MoviePoster",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                )
                .padding(8.dp)
        ) {
            Text(
                text = movie.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rate"
                )
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                Text(
                    text = String.format("%.1f", movie.voteAverage),
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

@Preview
@Composable
private fun PreviewTopRatedItemList() {
    TopRatedItemList(movie = Movie) { }
}

@Preview
@Composable
private fun PreviewDiscoverItemList() {
    DiscoverItemList(movie = Movie) { }
}