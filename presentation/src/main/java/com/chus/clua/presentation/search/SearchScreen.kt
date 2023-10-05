package com.chus.clua.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.chus.clua.presentation.model.MovieList

@Composable
fun SearchScreenRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues
) {

    val state = viewModel.searchState.collectAsStateWithLifecycle()

    SearchScreen(
        movies = state.value.movies,
        onQueryChanged = viewModel::search,
        onMovieClick = onMovieClick,
        paddingValues = paddingValues
    )

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
private fun SearchScreen(
    movies: List<MovieList>,
    onQueryChanged: (String) -> Unit,
    onMovieClick: (Int) -> Unit,
    paddingValues: PaddingValues,
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

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                showSearchView = true
                return super.onPostFling(consumed, available)
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedVisibility(
            visible = showSearchView,
            enter = expandVertically(
                expandFrom = Alignment.CenterVertically,
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
        LazyColumn(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                .nestedScroll(nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(movies.size) { index ->
                movies[index].let { movie ->
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
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
                .width(100.dp)
        )
        Column(
            modifier = Modifier
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                )
                .fillMaxHeight()
                .weight(1F)
                .padding(start = 16.dp, top = 16.dp, bottom = 8.dp, end = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = movie.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
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