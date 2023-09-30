package com.chus.clua.presentation.composable

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.chus.clua.presentation.navigation.BottomNavigationScreens
import com.chus.clua.presentation.navigation.currentRoute

@Composable
fun MainBottomAppBar(
    navController: NavHostController,
    bottomNavigationScreens: List<BottomNavigationScreens>
) {
    BottomAppBar {
        val currentRoute = navController.currentRoute()
        bottomNavigationScreens.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        screen.vector,
                        "",
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                label = { Text(stringResource(id = screen.title)) },
                selected = currentRoute == screen.route,
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