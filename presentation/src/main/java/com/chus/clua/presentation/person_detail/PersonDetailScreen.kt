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
import com.chus.clua.presentation.composable.ExpandableText
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

@OptIn(ExperimentalGlideComposeApi::class)
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

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {

            Box {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                GlideImage(
                    model = detail?.profilePath.orEmpty(),
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
                    text = detail?.name.orEmpty(),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(all = 8.dp),
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
                            model = movie.posterPath,
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
private fun PreviewPersonResume() {
    PersonResume(
        name = "Marlon Brando",
        biography = "Marlon Brando Jr. (April 3, 1924 – July 1, 2004) was an American actor. Considered one of the most influential actors of the 20th century, he received numerous accolades throughout his career which spanned six decades, including two Academy Awards, two Golden Globe Awards, and three British Academy Film Awards. Brando was also an activist for many causes, notably the civil rights movement and various Native American movements. Having studied with Stella Adler in the 1940s, he is credited with being one of the first actors to bring the Stanislavski system of acting and method acting, derived from the Stanislavski system, to mainstream audiences.\n\nHe initially gained acclaim and his first Academy Award nomination for Best Actor in a Leading Role for reprising the role of Stanley Kowalski in the 1951 film adaptation of Tennessee Williams' play A Streetcar Named Desire, a role that he originated successfully on Broadway. He received further praise, and a first Academy Award and Golden Globe Award, for his performance as Terry Malloy in On the Waterfront, and his portrayal of the rebellious motorcycle gang leader Johnny Strabler in The Wild One proved to be a lasting image in popular culture. Brando received Academy Award nominations for playing Emiliano Zapata in Viva Zapata! (1952); Mark Antony in Joseph L. Mankiewicz's 1953 film adaptation of Shakespeare's Julius Caesar; and Air Force Major Lloyd Gruver in Sayonara (1957), an adaptation of James A. Michener's 1954 novel.\n\nThe 1960s saw Brando's career take a commercial and critical downturn. He directed and starred in the cult western One-Eyed Jacks, a critical and commercial flop, after which he delivered a series of notable box-office failures, beginning with Mutiny on the Bounty (1962). After ten years of underachieving, he agreed to do a screen test as Vito Corleone in Francis Ford Coppola's The Godfather (1972). He got the part and subsequently won his second Academy Award and Golden Globe Award in a performance critics consider among his greatest. He declined the Academy Award due to alleged mistreatment and misportrayal of Native Americans by Hollywood. The Godfather was one of the most commercially successful films of all time, and alongside his Oscar-nominated performance in Last Tango in Paris (1972), Brando reestablished himself in the ranks of top box-office stars.\n\nAfter a hiatus in the early 1970s, Brando was generally content with being a highly paid character actor in supporting roles, such as Jor-El in Superman (1978), as Colonel Kurtz in Apocalypse Now (1979), and Adam Steiffel in The Formula (1980), before taking a nine-year break from film. According to the Guinness Book of World Records, Brando was paid a record $3.7 million ($16 million in inflation-adjusted dollars) and 11.75% of the gross profits for 13 days' work on Superman.\n\nBrando was ranked by the American Film Institute as the fourth-greatest movie star among male movie stars whose screen debuts occurred in or before 1950. He was one of only six actors named in 1999 by Time magazine in its list of the 100 Most Important People of the Century. In this list, Time also designated Brando as the \"Actor of the Century\".",
        homepage = "https://www.marlonbrando.com/",
        onHomePageClicked = { _, _ -> }
    )
}