package com.chus.clua.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.compose.composables.AppEmptyScreen
import com.chus.clua.presentation.model.MovieList

private const val SEARCHBAR_DELAY = 500

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (movieId: Int) -> Unit,
    paddingValues: PaddingValues
) {

    val state = viewModel.searchState.collectAsStateWithLifecycle()

    SearchScreen(
        movies = state.value.movies,
        error = state.value.error,
        onQueryChanged = viewModel::search,
        onMovieClick = onMovieClick,
        paddingValues = paddingValues
    )

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    movies: List<MovieList>,
    error: Boolean,
    onQueryChanged: (query: String) -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
    paddingValues: PaddingValues,
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val keyboardController = LocalSoftwareKeyboardController.current
        var text by rememberSaveable { mutableStateOf("") }
        var showSearchView by rememberSaveable { mutableStateOf(true) }

        val nestedScrollConnection = remember {
            object : NestedScrollConnection {
                override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                    showSearchView = false
                    return super.onPreScroll(available, source)
                }

                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    showSearchView = true
                    return super.onPostFling(consumed, available)
                }
            }
        }

        AnimatedVisibility(
            visible = showSearchView,
            enter = expandVertically(
                expandFrom = Alignment.CenterVertically,
                animationSpec = tween(delayMillis = SEARCHBAR_DELAY)
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.CenterVertically
            ),
        ) {
            SearchBar(
                modifier = Modifier
                    .semantics { traversalIndex = -1f },
                query = text,
                onQueryChange = {
                    text = it
                    onQueryChanged(text)
                },
                onSearch = {
                    keyboardController?.hide()
                },
                active = false,
                onActiveChange = { },
                placeholder = { Text(stringResource(id = R.string.search)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            ) {}
        }
        if (error) {
            AppEmptyScreen(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Search,
                message = stringResource(id = R.string.empty_search),
                paddingValues = paddingValues
            )
        } else {
            SearchList(
                movies = movies,
                onMovieClick = onMovieClick,
                nestedScrollConnection = nestedScrollConnection
            )
        }
    }

}

@Composable
private fun SearchList(
    movies: List<MovieList>,
    onMovieClick: (movieId: Int) -> Unit,
    nestedScrollConnection: NestedScrollConnection
) {
    LazyColumn(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
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
private fun MovieItemList(
    movie: MovieList,
    onMovieClick: (movieId: Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onMovieClick(movie.id)
            }
    ) {
        GlideImage(
            model = movie.posterPath,
            contentScale = ContentScale.Crop,
            contentDescription = "MoviePoster",
            modifier = Modifier
                .fillMaxHeight()
                .width(140.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1F)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clickable {
                    onMovieClick(movie.id)
                }
        ) {
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
                    style = MaterialTheme.typography.titleMedium
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
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSearchScreen() {
    SearchScreen(
        movies = listOf(Movie, Movie),
        error = false,
        onQueryChanged = {},
        onMovieClick = {},
        paddingValues = PaddingValues()
    )
}

@Preview
@Composable
private fun PreviewEmptySearchScreen() {
    SearchScreen(
        movies = emptyList(),
        error = true,
        onQueryChanged = {},
        onMovieClick = {},
        paddingValues = PaddingValues()
    )
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