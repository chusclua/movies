package com.chus.clua.presentation.compose.composables

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chus.clua.presentation.navigation.BottomNavigationScreens
import com.chus.clua.presentation.navigation.currentRoute

@Composable
fun MainBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomNavigationScreens: List<BottomNavigationScreens>
) {
    BottomAppBar(modifier = modifier) {
        val currentRoute = navController.currentRoute()
        bottomNavigationScreens.forEach { screen ->
            val selected = currentRoute == screen.route
            val color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onPrimaryContainer
            }
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.vector,
                        stringResource(id = screen.title),
                        tint = color
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = screen.title),
                        color = color
                    )
                },
                selected = selected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMainBottomAppBar() {
    val bottomNavigationScreens = listOf(
        BottomNavigationScreens.Movies,
        BottomNavigationScreens.Favorites,
        BottomNavigationScreens.Search
    )
    val navController = rememberNavController()
    MainBottomAppBar(
        navController = navController,
        bottomNavigationScreens = bottomNavigationScreens
    )
}
