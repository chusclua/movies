package com.chus.clua.presentation.moviedetail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.model.CastList
import com.chus.clua.presentation.model.CrewList
import com.chus.clua.presentation.model.MovieDetail
import com.chus.clua.presentation.model.People

@Composable
fun DetailScreenRoute(
    onBackClick: () -> Unit,
    onPeopleClick: (Int) -> Unit
) {

    val viewModel: MovieDetailViewModel = hiltViewModel()
    val state = viewModel.detailState.collectAsStateWithLifecycle()

    DetailScreen(
        detail = state.value.movieDetail,
        cast = state.value.cast,
        crew = state.value.crew,
        error = state.value.error,
        onBackClick = onBackClick,
        onFavClick = viewModel::toggleOnFavorites,
        onPeopleClick = onPeopleClick
    )

}

@Composable
private fun DetailScreen(
    detail: MovieDetail?,
    cast: List<CastList>,
    crew: List<CrewList>,
    error: Boolean,
    onBackClick: () -> Unit,
    onFavClick: () -> Unit,
    onPeopleClick: (Int) -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }

    if (error) {
        val toastMessage = stringResource(id = R.string.detail_error)
        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(toastMessage)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->

        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

            MovieHeader(
                backdropPath = detail?.backdropPath,
                onBackClick = onBackClick,
                onFavClick = onFavClick
            )

            MovieResume(detail = detail)

            MoviePeopleList(
                title = stringResource(id = R.string.detail_cast),
                people = cast,
                onPeopleClick = onPeopleClick
            )

            MoviePeopleList(
                title = stringResource(id = R.string.detail_crew),
                people = crew,
                onPeopleClick = onPeopleClick
            )

            MovieProductionDetail(
                productionCompanies = detail?.productionCompanies,
                productionCountries = detail?.productionCountries
            )

        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MovieHeader(
    backdropPath: String?,
    onBackClick: () -> Unit,
    onFavClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        GlideImage(
            model = backdropPath,
            contentScale = ContentScale.Crop,
            contentDescription = "MovieBackDrop",
            modifier = Modifier.fillMaxSize(),
        )
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        FloatingActionButton(
            onClick = { onFavClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
        ) {
            Icon(Icons.Filled.Favorite, null)
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MovieResume(detail: MovieDetail?) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
                .height(160.dp)
        ) {
            GlideImage(
                model = detail?.posterPath,
                contentScale = ContentScale.Crop,
                contentDescription = "MoviePoster",
                modifier = Modifier
                    .width(width = 100.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxHeight()
            ) {
                Text(
                    text = detail?.title.orEmpty(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = detail?.tagline.orEmpty(),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(vertical = 4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = detail?.genres ?: emptyList()) { genre ->
                        Text(
                            text = genre,
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    RoundedCornerShape(40.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1F))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = detail?.releaseDate.orEmpty(),
                        modifier = Modifier.weight(1F),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Rate"
                    )
                    Spacer(modifier = Modifier.padding(horizontal = 2.dp))
                    Text(
                        text = String.format("%.1f", detail?.voteAverage),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Text(
            text = detail?.overview.orEmpty(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Divider(modifier = Modifier.padding(top = 16.dp))
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
private fun MoviePeopleList(
    title: String,
    people: List<People>,
    onPeopleClick: (Int) -> Unit
) {
    Column {

        Text(
            text = title,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = people) { people ->
                val toolTipText = when (people) {
                    is CastList -> "${people.name} ${stringResource(id = R.string.detail_as)} ${people.character}"
                    is CrewList -> "${people.job}: ${people.name}"
                    else -> ""
                }
                PlainTooltipBox(
                    tooltip = { Text(toolTipText) }
                ) {
                    GlideImage(
                        model = people.profilePath,
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        modifier = Modifier
                            .border(4.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                            .clip(CircleShape)
                            .size(120.dp)
                            .tooltipAnchor()
                            .clickable {
                                onPeopleClick(people.id)
                            }
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieProductionDetail(
    productionCompanies: List<String>?,
    productionCountries: List<String>?
) {

    Column(modifier = Modifier.padding(vertical = 16.dp)) {

        Divider(modifier = Modifier.padding(bottom = 8.dp),)

        val companies = pluralStringResource(id = R.plurals.detail_companies, count = productionCompanies?.size ?: 0)

        Text(
            text = companies,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = productionCompanies?.joinToString(" | ").orEmpty(),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )

        Divider(modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))

        val countries = pluralStringResource(id = R.plurals.detail_countries, count = productionCountries?.size ?: 0)

        Text(
            text = countries,
            modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = productionCountries?.joinToString(" | ").orEmpty(),
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun PreviewMovieHeader() {
    MovieHeader(
        backdropPath = MovieDetail.backdropPath,
        onBackClick = {},
        onFavClick = {}
    )
}

@Preview
@Composable
private fun PreviewMovieResume() {
    MovieResume(detail = MovieDetail)
}

@Preview
@Composable
private fun PreviewMoviePeopleList() {
    MoviePeopleList(
        title = "Cast",
        people = listOf(CastList, CastList),
        onPeopleClick = {}
    )
}

@Preview
@Composable
private fun PreviewMovieProductionDetail() {
    MovieProductionDetail(
        productionCompanies = MovieDetail.productionCompanies,
        productionCountries = MovieDetail.productionCountries
    )
}

val MovieDetail = MovieDetail(
    backdropPath = "/tmU7GeKVybMWFButWEGl2M4GeiP.jpg",//
    genres = listOf("Drama", "Crime"),//
    id = 238,
    overview = "Spanning the years 1945 to 1955, a chronicle of the fictional Italian-American Corleone crime family. When organized crime family patriarch, Vito Corleone barely survives an attempt on his life, his youngest son, Michael steps in to take care of the would-be killers, launching a campaign of bloody revenge.",
    posterPath = "/3bhkrj58Vtu7enYsRolD1fZdja1.jpg",//
    productionCompanies = listOf("Paramount", "Alfran Productions"),
    productionCountries = listOf("United States of America"),
    releaseDate = "1972-03-14",//
    tagline = "An offer you can't refuse.",//
    title = "The Godfather",//
    voteAverage = 8.707//
)

val CastList = CastList(
    id = 3084,
    character = "Don Vito Corleone",
    name = "Marlon Brando",
    profilePath = "/eEHCjqKMWSvQU4bmwhLMsg4RtEr.jpg"
)