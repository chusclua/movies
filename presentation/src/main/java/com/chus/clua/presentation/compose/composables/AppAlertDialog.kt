package com.chus.clua.presentation.compose.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.chus.clua.presentation.R

@Composable
fun AppAlertDialog(
    title: String? = null,
    icon: ImageVector? = null,
    message: String,
    onDismissRequest: () -> Unit,
    dismissText: String? = null,
    onConfirmation: () -> Unit,
    confirmText: String? = null
) {
    AlertDialog(
        icon = {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        title = {
            title?.let {
                Text(
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Text(text = message)
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(
                onClick = onConfirmation
            ) {
                Text(confirmText ?: stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(dismissText ?: stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
private fun PreviewAppAlertDialog() {
    AppAlertDialog(
        icon = Icons.Default.Info,
        title = stringResource(id = R.string.favorites_delete_all_title),
        message = stringResource(id = R.string.favorites_delete_all_message),
        onDismissRequest = { },
        onConfirmation = { }
    )
}
