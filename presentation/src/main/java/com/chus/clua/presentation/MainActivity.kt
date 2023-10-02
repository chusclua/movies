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
import androidx.navigation.compose.rememberNavController
import com.chus.clua.presentation.composable.MainBottomAppBar
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
fun MainScreen() {

    val navController = rememberNavController()

    val bottomNavigationItems = listOf(
        BottomNavigationScreens.Movies,
        BottomNavigationScreens.Favorites,
        BottomNavigationScreens.Search
    )

    val shouldShowMainBars =
        navController.shouldShowMainBottomBar(*bottomNavigationItems.map { it.route }.toTypedArray())

    Scaffold(
        bottomBar = {
            if (shouldShowMainBars) {
                MainBottomAppBar(navController, bottomNavigationItems)
            }
        },
    ) { paddingValues ->
        NavGraph(navController, paddingValues)
    }
}