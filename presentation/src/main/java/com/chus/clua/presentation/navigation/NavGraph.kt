package com.chus.clua.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.chus.clua.presentation.detail.DetailScreenRoute
import com.chus.clua.presentation.favorites.FavoritesScreenRoute
import com.chus.clua.presentation.movies.MoviesScreenRoute
import com.chus.clua.presentation.search.SearchScreenRoute

@Composable
fun NavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigationScreens.Movies.route
    ) {
        composable(route = BottomNavigationScreens.Movies.route) {
            MoviesScreenRoute(navController::navigateToDetail, paddingValues)
        }
        composable(route = BottomNavigationScreens.Favorites.route) {
            FavoritesScreenRoute(paddingValues)
        }
        composable(route = BottomNavigationScreens.Search.route) {
            SearchScreenRoute(navController::navigateToDetail, paddingValues)
        }
        composable(
            route = NavigationScreens.DETAIL.route,
            arguments = listOf(
                navArgument("movie_id") {
                    type = NavType.IntType
                }
            )
        ) { navBackStackEntry ->
            DetailScreenRoute(
                id = navBackStackEntry.arguments?.getInt("movie_id")
            )
        }
    }
}

private fun NavController.navigateToDetail(movieId: Int) {
    navigate("detail/$movieId")
}

@Composable
fun NavHostController.shouldShowMainBottomBar(vararg routes: String): Boolean {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route.toString()
    return route in routes
}

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}