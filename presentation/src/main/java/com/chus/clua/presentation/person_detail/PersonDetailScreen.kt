package com.chus.clua.presentation.person_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.chus.clua.presentation.R
import com.chus.clua.presentation.composable.ExpandableText
import com.chus.clua.presentation.model.PersonDetailUi


@Composable
fun PeopleDetailScreenRoute(
    viewModel: PersonDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onHomePageClicked: (String, String) -> Unit
) {

    val state = viewModel.detailState.collectAsStateWithLifecycle()

    PeopleDetailScreen(
        detail = state.value.detail,
        error = state.value.error,
        onBackClick = onBackClick,
        onHomePageClicked = onHomePageClicked
    )

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PeopleDetailScreen(
    detail: PersonDetailUi?,
    error: Boolean,
    onBackClick: () -> Unit,
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primaryContainer)
        ) {
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

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
            ) {

                PersonResume(
                    name = detail?.name,
                    biography = detail?.biography,
                    homepage = detail?.homepage,
                    onHomePageClicked = onHomePageClicked
                )

                Spacer(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = "sdgfhsdfsdhfsdfgsd shdjkhsfhsdhfjksdhjfhsdjfkhsdj shdjkhskdjhfjkshfjksdhfjksdhfjksdhfjkhsdfjkhsjfkhdsj hjskdfhjksdhfjksdhfjkhsdjkfhsdjfhsdjkfhsdjkfhsd jhsdjhfjksdhfkjsdhfjkd sdgfhsdfsdhfsdfgsd shdjkhsfhsdhfjksdhjfhsdjfkhsdj shdjkhskdjhfjkshfjksdhfjksdhfjksdhfjkhsdfjkhsjfkhdsj hjskdfhjksdhfjksdhfjkhsdjkfhsdjfhsdjkfhsdjkfhsd jhsdjhfjksdhfkjsdhfjkd sdgfhsdfsdhfsdfgsd shdjkhsfhsdhfjksdhjfhsdjfkhsdj shdjkhskdjhfjkshfjksdhfjksdhfjksdhfjkhsdfjkhsjfkhdsj hjskdfhjksdhfjksdhfjkhsdjkfhsdjfhsdjkfhsdjkfhsd jhsdjhfjksdhfkjsdhfjkd sdgfhsdfsdhfsdfgsd shdjkhsfhsdhfjksdhjfhsdjfkhsdj shdjkhskdjhfjkshfjksdhfjksdhfjksdhfjkhsdfjkhsjfkhdsj hjskdfhjksdhfjksdhfjkhsdjkfhsdjfhsdjkfhsdjkfhsd jhsdjhfjksdhfkjsdhfjkd sdgfhsdfsdhfsdfgsd shdjkhsfhsdhfjksdhjfhsdjfkhsdj shdjkhskdjhfjkshfjksdhfjksdhfjksdhfjkhsdfjkhsjfkhdsj hjskdfhjksdhfjksdhfjkhsdjkfhsdjfhsdjkfhsdjkfhsd jhsdjhfjksdhfkjsdhfjkd sdgfhsdfsdhfsdfgsd shdjkhsfhsdhfjksdhjfhsdjfkhsdj shdjkhskdjhfjkshfjksdhfjksdhfjksdhfjkhsdfjkhsjfkhdsj hjskdfhjksdhfjksdhfjkhsdjkfhsdjfhsdjkfhsdjkfhsd jhsdjhfjksdhfkjsdhfjkd")
            }

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
        Text(
            text = name.orEmpty(),
            color = Color.White,
            style = MaterialTheme.typography.displayMedium
        )
        Spacer(modifier = Modifier.padding(vertical = 8.dp))
        ExpandableText(
            text = biography.orEmpty(),
            textColor = Color.White,
            textAccentColor = MaterialTheme.colorScheme.inversePrimary,
            style = MaterialTheme.typography.bodyLarge
        )
        homepage?.let { homePage ->
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onHomePageClicked(homePage, name.orEmpty()) }
                )) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    color = Color.White
                )
            }
        }

    }
}