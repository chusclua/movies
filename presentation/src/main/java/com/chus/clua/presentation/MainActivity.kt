package com.chus.clua.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.chus.clua.presentation.compose.composables.MainBottomAppBar
import com.chus.clua.presentation.navigation.BottomNavigationScreens
import com.chus.clua.presentation.navigation.NavGraph
import com.chus.clua.presentation.navigation.shouldShowMainBottomBar
import com.chus.clua.presentation.ui.theme.MoviesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
private fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavigationScreens = listOf(
        BottomNavigationScreens.Movies,
        BottomNavigationScreens.Favorites,
        BottomNavigationScreens.Search
    )

    val shouldShowMainBars =
        navController.shouldShowMainBottomBar(bottomNavigationScreens.map { it.route })

    Scaffold(
        bottomBar = {
            if (shouldShowMainBars) {
                MainBottomAppBar(
                    modifier = Modifier,
                    navController = navController,
                    bottomNavigationScreens = bottomNavigationScreens
                )
            }
        },
    ) { paddingValues ->
        NavGraph(navController, paddingValues)
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        MainScreen()
    }
}
