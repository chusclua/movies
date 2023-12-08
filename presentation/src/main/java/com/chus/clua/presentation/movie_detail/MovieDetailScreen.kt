package com.chus.clua.presentation.movie_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
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
import com.chus.clua.presentation.compose.Cast
import com.chus.clua.presentation.compose.MovieDetail
import com.chus.clua.presentation.compose.Video
import com.chus.clua.presentation.compose.composables.ExpandableText
import com.chus.clua.presentation.model.CastList
import com.chus.clua.presentation.model.CrewList
import com.chus.clua.presentation.model.MovieDetailUi
import com.chus.clua.presentation.model.PersonList
import com.chus.clua.presentation.model.VideoList

@Composable
fun MovieDetailScreenRoute(
    viewModel: MovieDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onVideoClick: (url: String, title: String) -> Unit,
    onPeopleClick: (peopleId: Int) -> Unit
) {

    val state = viewModel.detailState.collectAsStateWithLifecycle()

    DetailScreen(
        isFavorite = state.value.isFavorite,
        detail = state.value.movieDetail,
        cast = state.value.cast,
        crew = state.value.crew,
        videos = state.value.videos,
        error = state.value.error,
        onBackClick = onBackClick,
        onVideoClick = onVideoClick,
        onFavClick = viewModel::toggleOnFavorites,
        onPeopleClick = onPeopleClick
    )

}

@Composable
private fun DetailScreen(
    isFavorite: Boolean,
    detail: MovieDetailUi?,
    cast: List<CastList>,
    crew: List<CrewList>,
    videos: List<VideoList>,
    error: Boolean,
    onBackClick: () -> Unit,
    onVideoClick: (url: String, title: String) -> Unit,
    onFavClick: () -> Unit,
    onPeopleClick: (peopleId: Int) -> Unit
) {

    val snackBarHostState = remember { SnackbarHostState() }

    if (error) {
        val toastMessage = stringResource(id = R.string.error_message)
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

        if (!error) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
            ) {

                MovieHeader(
                    backdropPath = detail?.backdropUrl,
                    isFavorite = isFavorite,
                    onBackClick = onBackClick,
                    onFavClick = onFavClick
                )

                MovieResume(
                    detail = detail,
                    videos = videos,
                    onVideoClick = onVideoClick
                )

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
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MovieHeader(
    backdropPath: String?,
    isFavorite: Boolean,
    onBackClick: () -> Unit,
    onFavClick: () -> Unit
) {
    val tileSize = with(LocalDensity.current) {
        100.dp.toPx()
    }
    val brush = Brush.verticalGradient(
        listOf(Color.Black.copy(alpha = 0.75F), Color.Transparent),
        endY = tileSize,
        tileMode = TileMode.Clamp
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        GlideImage(
            model = backdropPath,
            contentScale = ContentScale.Crop,
            contentDescription = "MovieBackDrop",
            modifier = Modifier.fillMaxSize().background(brush),
        )
        IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.TopStart)) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        val favIcon = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder
        FloatingActionButton(
            onClick = { onFavClick() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 16.dp, end = 16.dp),
        ) {
            Icon(
                imageVector = favIcon,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun MovieResume(
    detail: MovieDetailUi?,
    videos: List<VideoList>,
    onVideoClick: (url: String, title: String) -> Unit,
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp)
        ) {
            GlideImage(
                model = detail?.posterUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "MoviePoster",
                modifier = Modifier
                    .size(width = 100.dp, height = 160.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.padding(horizontal = 8.dp))
            Column(
                modifier = Modifier.defaultMinSize(minHeight = 160.dp)
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
                    items(items = detail?.genres.orEmpty()) { genre ->
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
                    modifier = Modifier.fillMaxWidth()
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

        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .align(CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(items = videos) { video ->
                VideoItemList(
                    video = video,
                    onVideoClick = onVideoClick
                )
            }
        }

        ExpandableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = detail?.overview.orEmpty(),
            style = MaterialTheme.typography.bodyLarge
        )
        Divider()
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
private fun MoviePeopleList(
    title: String,
    people: List<PersonList>,
    onPeopleClick: (peopleId: Int) -> Unit
) {

    if (people.isNotEmpty()) {
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
                            model = people.profileUrl,
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .border(4.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                                .clip(CircleShape)
                                .size(120.dp)
                                .tooltipAnchor()
                                .clickable { onPeopleClick(people.id) }
                        )
                    }
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

        Divider(modifier = Modifier.padding(bottom = 8.dp))

        val companies = pluralStringResource(
            id = R.plurals.detail_companies,
            count = productionCompanies?.size ?: 0
        )

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

        val countries = pluralStringResource(
            id = R.plurals.detail_countries,
            count = productionCountries?.size ?: 0
        )

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

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun VideoItemList(
    video: VideoList,
    onVideoClick: (url: String, title: String) -> Unit,
) {
    Box(
        modifier = Modifier
            .size(width = 120.dp, height = 80.dp)
            .border(1.dp, MaterialTheme.colorScheme.onBackground, RoundedCornerShape(8.dp))
            .clickable { onVideoClick(video.youtubeUrl, video.name) }
    ) {
        GlideImage(
            model = video.thumbnailUrl,
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .align(Center)
        )
        Box(
            modifier = Modifier
                .size(width = 24.dp, height = 16.dp)
                .background(
                    Color.Red,
                    RoundedCornerShape(4.dp)
                )
                .align(Center)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "",
                modifier = Modifier
                    .size(10.dp)
                    .align(Center),
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMovieHeader() {
    MovieHeader(
        backdropPath = MovieDetail.backdropUrl,
        isFavorite = true,
        onBackClick = {},
        onFavClick = {}
    )
}

@Preview
@Composable
private fun PreviewMovieResume() {
    MovieResume(
        detail = MovieDetail,
        videos = listOf(Video),
        onVideoClick = { _, _ -> }
    )
}

@Preview
@Composable
private fun PreviewMovieItemList() {
    VideoItemList(
        video = Video,
        onVideoClick = { _, _ -> }
    )
}

@Preview
@Composable
private fun PreviewMoviePeopleList() {
    MoviePeopleList(
        title = "Cast",
        people = listOf(Cast, Cast),
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