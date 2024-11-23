package com.chus.clua.presentation.webview

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chus.clua.presentation.compose.composables.AppTopBar
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(
    title: String?,
    url: String?,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(
            title = title.orEmpty(),
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
        val webViewState = rememberWebViewState(url = url.orEmpty())
        WebView(
            modifier = Modifier
                .fillMaxSize(),
            state = webViewState,
            captureBackPresses = false,
            onCreated = { webView ->
                webView.settings.javaScriptEnabled = true
            },
        )
    }
}

@Preview
@Composable
private fun PreviewNewsWebViewScreen() {
    WebViewScreen(
        title = "The GodFather",
        url = "",
        onBackClick = {}
    )
}
