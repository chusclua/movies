package com.chus.clua.presentation.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.model.MovieList

@Composable
fun MoviesScreenRoute(
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {

    val viewModel: MoviesViewModel = hiltViewModel()
    val movies = viewModel.moviesFlow.collectAsLazyPagingItems()

    MoviesScreen(
        movies = movies,
        onMovieClick = onMovieClick,
        paddingValues = paddingValues
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesScreen(
    movies: LazyPagingItems<MovieList>,
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues,
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
                    text = stringResource(id = R.string.movies),
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
            items(movies.itemCount) { index ->
                movies[index]?.let { movie ->
                    MovieItemList(movie = movie, onMovieClick = onMovieClick)
                }
            }
        }
    }


}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun MovieItemList(
    movie: MovieList,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onMovieClick(movie.id)
        }
    ) {
        GlideImage(
            model = movie.posterPath,
            contentScale = ContentScale.Crop,
            contentDescription = "MoviePoster",
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
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
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.year,
                    modifier = Modifier.weight(1F),
                    style = MaterialTheme.typography.titleMedium
                )
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
private fun PreviewMovieItemList() {
    MovieItemList(movie = Movie) { }
}

private val Movie = MovieList(
    id = 238,
    title = "The Godfather Part II",
    posterPath = "https://image.tmdb.org/t/p/w342/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",
    year = "1972",
    voteAverage = 8.7,
)