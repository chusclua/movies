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
import com.chus.clua.presentation.movie_detail.MovieDetailScreenRoute
import com.chus.clua.presentation.favorites.FavoritesScreenRoute
import com.chus.clua.presentation.movies.MoviesScreenRoute
import com.chus.clua.presentation.person_detail.PeopleDetailScreenRoute
import com.chus.clua.presentation.search.SearchScreenRoute
import com.chus.clua.presentation.webview.WebViewScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

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
            MoviesScreenRoute(
                onMovieClick = navController::navigateToMovieDetail,
                paddingValues = paddingValues
            )
        }
        composable(route = BottomNavigationScreens.Favorites.route) {
            FavoritesScreenRoute(
                onMovieClick = navController::navigateToMovieDetail,
                paddingValues = paddingValues
            )
        }
        composable(route = BottomNavigationScreens.Search.route) {
            SearchScreenRoute(
                onMovieClick = navController::navigateToMovieDetail,
                paddingValues = paddingValues
            )
        }
        composable(
            route = NavigationScreens.MovieDetails.route,
            arguments = listOf(
                navArgument(NavigationScreens.MovieDetails.paramId) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreenRoute(
                onBackClick = navController::popBackStack,
                onVideoClick = navController::navigateToWebView,
                onPeopleClick = navController::navigateToPeopleDetail
            )
        }
        composable(
            route = NavigationScreens.PeopleDetail.route,
            arguments = listOf(
                navArgument(NavigationScreens.PeopleDetail.paramId) {
                    type = NavType.IntType
                }
            )
        ) {
            PeopleDetailScreenRoute(
                onBackClick = navController::popBackStack,
                onHomePageClicked = navController::navigateToWebView
            )
        }
        composable(
            route = NavigationScreens.WebView.route,
            arguments = listOf(
                navArgument(NavigationScreens.WebView.paramUrl) {
                    type = NavType.StringType
                },
                navArgument(NavigationScreens.WebView.paramTitle) {
                    type = NavType.StringType
                }
            )) { navBackStackEntry ->
            WebViewScreen(
                title = navBackStackEntry.arguments?.getString(NavigationScreens.WebView.paramTitle),
                url = navBackStackEntry.arguments?.getString(NavigationScreens.WebView.paramUrl),
                onBackClick = navController::popBackStack,
            )
        }
    }
}

private fun NavController.navigateToMovieDetail(movieId: Int) {
    navigate("movie-detail/$movieId")
}

private fun NavController.navigateToPeopleDetail(peopleId: Int) {
    navigate("people-detail/$peopleId")
}

private fun NavController.navigateToWebView(url: String, title: String) {
    val encodedUrl = url.encode()
    navigate("webview/$encodedUrl/$title")
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

private fun String.encode() = URLEncoder.encode(this, StandardCharsets.UTF_8.toString())