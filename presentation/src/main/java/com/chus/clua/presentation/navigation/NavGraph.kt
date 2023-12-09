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
import com.chus.clua.presentation.extensions.encode
import com.chus.clua.presentation.movie_detail.MovieDetailScreenRoute
import com.chus.clua.presentation.favorites.FavoritesScreenRoute
import com.chus.clua.presentation.movies.MoviesScreenRoute
import com.chus.clua.presentation.person_detail.PeopleDetailScreenRoute
import com.chus.clua.presentation.search.SearchScreenRoute
import com.chus.clua.presentation.webview.WebViewScreen

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
            route = Screens.MovieDetail.route,
            arguments = listOf(
                navArgument(Screens.MovieDetail.paramId) {
                    type = NavType.IntType
                }
            )
        ) {
            MovieDetailScreenRoute(
                onBackClick = navController::popBackStack,
                onVideoClick = navController::navigateToWebView,
                onPeopleClick = navController::navigateToPersonDetail
            )
        }
        composable(
            route = Screens.PersonDetail.route,
            arguments = listOf(
                navArgument(Screens.PersonDetail.paramId) {
                    type = NavType.IntType
                }
            )
        ) {
            PeopleDetailScreenRoute(
                onBackClick = navController::popBackStack,
                onMovieClick = navController::navigateToMovieDetail,
                onHomePageClicked = navController::navigateToWebView
            )
        }
        composable(
            route = Screens.WebView.route,
            arguments = listOf(
                navArgument(Screens.WebView.paramUrl) {
                    type = NavType.StringType
                },
                navArgument(Screens.WebView.paramTitle) {
                    type = NavType.StringType
                }
            )) { navBackStackEntry ->
            WebViewScreen(
                title = navBackStackEntry.arguments?.getString(Screens.WebView.paramTitle),
                url = navBackStackEntry.arguments?.getString(Screens.WebView.paramUrl),
                onBackClick = navController::popBackStack,
            )
        }
    }
}

private fun NavController.navigateToMovieDetail(movieId: Int) {
    navigate(
        Screens.MovieDetail.route.replace(
            "{${Screens.MovieDetail.paramId}}",
            movieId.toString()
        )
    )
}

private fun NavController.navigateToPersonDetail(personId: Int) {
    navigate(
        Screens.PersonDetail.route.replace(
            "{${Screens.PersonDetail.paramId}}",
            personId.toString()
        )
    )
}

private fun NavController.navigateToWebView(url: String, title: String) {
    navigate(
        Screens.WebView.route
            .replace(
                "{${Screens.WebView.paramUrl}}",
                url.encode()
            ).replace(
                "{${Screens.WebView.paramTitle}}",
                title
            )
    )
}

@Composable
fun NavHostController.shouldShowMainBottomBar(routes: List<String>): Boolean {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route.toString()
    return route in routes
}

@Composable
fun NavHostController.currentRoute(): String? {
    val navBackStackEntry by this.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}