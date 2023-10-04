package com.chus.clua.presentation.navigation


enum class NavigationScreens(val route: String, val param: String) {
    MOVIE_DETAIL(route = "movie-detail/{movie_id}", param = "movie_id"),
    PEOPLE_DETAIL(route = "people-detail/{people_id}", param = "people_id"),
    WEBVIEW(route = "webview/{url}", param = "url")
}