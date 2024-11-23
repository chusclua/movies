package com.chus.clua.presentation.compose.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun AppEmptyScreen(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    message: String,
    paddingValues: PaddingValues? = null
) {
    Column(
        modifier = modifier.padding(paddingValues ?: PaddingValues()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = message,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Preview
@Composable
private fun PreviewAppEmptyScreen() {
    AppEmptyScreen(
        modifier = Modifier.fillMaxSize(),
        imageVector = Icons.Default.Info,
        message = "This is an empty screen"
    )
}
