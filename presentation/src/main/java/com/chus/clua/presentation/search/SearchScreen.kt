package com.chus.clua.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
import com.chus.clua.presentation.compose.Movie
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
        empty = state.value.empty,
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
    empty: Boolean,
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
        var showSearchView by remember { mutableStateOf(true) }

        val onScrollList: (Boolean) -> Unit= { isScrolling ->
            showSearchView = !isScrolling
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
        if (empty || error) {
            AppEmptyScreen(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Search,
                message = stringResource(id = if (error) R.string.error_message else R.string.empty_search),
                paddingValues = paddingValues
            )
        } else {
            SearchList(
                movies = movies,
                onMovieClick = onMovieClick,
                onScrollList = onScrollList
            )
        }
    }

}

@Composable
private fun SearchList(
    movies: List<MovieList>,
    onMovieClick: (movieId: Int) -> Unit,
    onScrollList: (isScrolling: Boolean) -> Unit,
) {

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                onScrollList(true)
                return super.onPreScroll(available, source)
            }

            override suspend fun onPostFling(
                consumed: Velocity,
                available: Velocity
            ): Velocity {
                onScrollList(false)
                return super.onPostFling(consumed, available)
            }
        }
    }

    val listState = rememberLazyListState()

    LaunchedEffect(movies) {
        listState.animateScrollToItem(0)
    }

    LazyColumn(
        modifier = Modifier
            .nestedScroll(nestedScrollConnection),
        state = listState,
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            MovieItemList(movie = movie, onMovieClick = onMovieClick)
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
            model = movie.posterUrl,
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
                .clickable {
                    onMovieClick(movie.id)
                }
        ) {
            GlideImage(
                model = movie.backdropUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "MovieBackDrop",
                alpha = 0.5F,
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
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
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
                    style = MaterialTheme.typography.titleLarge.copy(
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
}

@Preview
@Composable
private fun PreviewSearchScreen() {
    SearchScreen(
        movies = listOf(Movie, Movie),
        empty = false,
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
        empty = true,
        error = false,
        onQueryChanged = {},
        onMovieClick = {},
        paddingValues = PaddingValues()
    )
}

@Preview
@Composable
private fun PreviewErrorSearchScreen() {
    SearchScreen(
        movies = emptyList(),
        empty = true,
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