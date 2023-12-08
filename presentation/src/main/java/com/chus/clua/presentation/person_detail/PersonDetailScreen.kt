package com.chus.clua.presentation.person_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.compose.PersonDetail
import com.chus.clua.presentation.compose.PersonMovieCast
import com.chus.clua.presentation.compose.composables.ExpandableText
import com.chus.clua.presentation.model.PersonDetailUi
import com.chus.clua.presentation.model.PersonMovieCastList
import com.chus.clua.presentation.model.PersonMovieCrewList
import com.chus.clua.presentation.model.PersonMovieList


@Composable
fun PeopleDetailScreenRoute(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
    onHomePageClicked: (url: String, name: String) -> Unit
) {

    val state = viewModel.detailState.collectAsStateWithLifecycle()

    PeopleDetailScreen(
        detail = state.value.detail,
        cast = state.value.cast,
        crew = state.value.crew,
        error = state.value.error,
        onBackClick = onBackClick,
        onMovieClick = onMovieClick,
        onHomePageClicked = onHomePageClicked
    )

}

@Composable
private fun PeopleDetailScreen(
    detail: PersonDetailUi?,
    cast: List<PersonMovieCastList>,
    crew: List<PersonMovieCrewList>,
    error: Boolean,
    onBackClick: () -> Unit,
    onMovieClick: (movieId: Int) -> Unit,
    onHomePageClicked: (String, String) -> Unit,
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
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .background(color = MaterialTheme.colorScheme.primaryContainer)
            ) {

                PersonHeader(
                    profilePath = detail?.profileUrl,
                    name = detail?.name,
                    onBackClick = onBackClick
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

                PersonResume(
                    name = detail?.name,
                    biography = detail?.biography,
                    homepage = detail?.homepage,
                    onHomePageClicked = onHomePageClicked
                )

                MovieList(
                    title = stringResource(id = R.string.detail_cast),
                    movies = cast,
                    onMovieClick = onMovieClick
                )

                MovieList(
                    title = stringResource(id = R.string.detail_crew),
                    movies = crew,
                    onMovieClick = onMovieClick
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))

            }

        }

    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun PersonHeader(
    profilePath: String?,
    name: String?,
    onBackClick: () -> Unit
) {
    Box {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.align(Alignment.TopStart)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
            )
        }

        GlideImage(
            model = profilePath.orEmpty(),
            contentScale = ContentScale.Crop,
            contentDescription = "PeopleProfile",
            modifier = Modifier
                .fillMaxWidth()
                .height(520.dp)
                .align(Alignment.TopEnd)
                .padding(start = 20.dp, top = 60.dp)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp)),
        )

        Text(
            text = name.orEmpty(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 28.dp, end = 8.dp, bottom = 8.dp),
            color = Color.White,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.displayMedium.copy(
                shadow = Shadow(
                    color = Color.Black.copy(alpha = 0.7F),
                    offset = Offset(5.0f, 10.0f),
                    blurRadius = 3f
                )
            )
        )

    }
}

@Composable
private fun PersonResume(
    name: String?,
    biography: String?,
    homepage: String?,
    onHomePageClicked: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(start = 80.dp, end = 8.dp)
    ) {
        ExpandableText(
            text = biography.orEmpty(),
            textColor = MaterialTheme.colorScheme.onPrimaryContainer,
            textAccentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.bodyLarge
        )
        homepage?.let { homePage ->
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = { onHomePageClicked(homePage, name.orEmpty()) }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 24.dp)
                        .background(
                            MaterialTheme.colorScheme.inversePrimary,
                            RoundedCornerShape(32.dp)
                        )
                ) {
                    Icon(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(16.dp),
                        imageVector = Icons.Outlined.ArrowForward,
                        contentDescription = "Website",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.padding(horizontal = 8.dp))
                Text(
                    text = stringResource(id = R.string.see_website),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class, ExperimentalMaterial3Api::class)
private fun MovieList(
    title: String,
    movies: List<PersonMovieList>,
    onMovieClick: (movieId: Int) -> Unit
) {

    if (movies.isNotEmpty()) {
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

                items(items = movies) { movie ->
                    val toolTipText = when (movie) {
                        is PersonMovieCastList -> "${
                            stringResource(id = R.string.detail_as).replaceFirstChar(
                                Char::titlecase
                            )
                        } ${movie.character}"

                        is PersonMovieCrewList -> "${movie.job}"
                        else -> ""
                    }
                    PlainTooltipBox(
                        tooltip = { Text(toolTipText) }
                    ) {
                        GlideImage(
                            model = movie.posterUrl,
                            contentScale = ContentScale.Crop,
                            contentDescription = null,
                            modifier = Modifier
                                .size(width = 100.dp, height = 140.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .tooltipAnchor()
                                .clickable { onMovieClick(movie.id) }
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun PreviewPersonHeader() {
    PersonHeader(
        profilePath = PersonDetail.profileUrl,
        name = PersonDetail.name,
        onBackClick = {}
    )
}

@Preview
@Composable
private fun PreviewPersonResume() {
    PersonResume(
        name = PersonDetail.name,
        biography = PersonDetail.biography,
        homepage = PersonDetail.homepage,
        onHomePageClicked = { _, _ -> }
    )
}

@Preview
@Composable
private fun PreviewMovieList() {
    MovieList(
        title = "Cast",
        movies = listOf(
            PersonMovieCast,
            PersonMovieCast,
            PersonMovieCast,
            PersonMovieCast
        ),
        onMovieClick = {}
    )
}