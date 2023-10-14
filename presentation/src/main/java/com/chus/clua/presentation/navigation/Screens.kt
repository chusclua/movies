package com.chus.clua.presentation.navigation


sealed class Screens(val route: String) {
    object MovieDetail: Screens(route = "movie-detail/{movie_id}") {
        const val  paramId = "movie_id"
    }
    object PeopleDetail: Screens(route = "people-detail/{people_id}") {
        const val paramId = "people_id"
    }
    object WebView: Screens(route = "webview/{url}/{title}") {
        const val paramUrl = "url"
        const val paramTitle = "title"
    }
}