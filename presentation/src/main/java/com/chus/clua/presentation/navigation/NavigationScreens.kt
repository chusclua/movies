package com.chus.clua.presentation.navigation


sealed class NavigationScreens(val route: String) {
    object MovieDetails: NavigationScreens(route = "movie-detail/{movie_id}") {
        const val  paramId = "movie_id"
    }
    object PeopleDetail: NavigationScreens(route = "people-detail/{people_id}") {
        const val paramId = "people_id"
    }
    object WebView: NavigationScreens(route = "webview/{url}/{title}") {
        const val paramUrl = "url"
        const val paramTitle = "title"
    }
}