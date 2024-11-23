package com.chus.clua.presentation.compose.composables

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chus.clua.presentation.R

private const val MINIMIZED_MAX_LINES = 3

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    textAccentColor: Color = MaterialTheme.colorScheme.primary,
    style: TextStyle = LocalTextStyle.current
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(buildAnnotatedString { append(text) }) }

    val showMore = stringResource(id = R.string.show_more)
    val showLess = stringResource(id = R.string.show_less)

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {

        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                val annotatedString = buildAnnotatedString {
                    append(text)
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = textAccentColor
                        )
                    ) {
                        append(showLess)
                    }
                }
                finalText = annotatedString
            }

            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(MINIMIZED_MAX_LINES - 1)
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMore.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                val annotatedString = buildAnnotatedString {
                    append(adjustedText)
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = textAccentColor
                        )
                    ) {
                        append(showMore)
                    }
                }

                finalText = annotatedString

                isClickable = true
            }
        }
    }

    Text(
        text = if (finalText.text.isEmpty()) buildAnnotatedString { append(text) } else finalText,
        modifier = modifier
            .clickable(
                enabled = isClickable,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { isExpanded = !isExpanded }
            .animateContentSize(animationSpec = tween(100)),
        color = textColor,
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        onTextLayout = { textLayoutResultState.value = it },

        style = style
    )
}

@Preview
@Composable
private fun PreviewExpandableText() {
    Column {
        ExpandableText(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin bibendum " +
                "nisi et ipsum consequat, at interdum orci gravida. Aliquam at metus nec nisl " +
                "suscipit hendrerit et at massa. Maecenas eget libero ex. Etiam finibus vel " +
                "nulla scelerisque cursus. Duis porta dignissim mauris ac elementum. Nulla " +
                "facilisi. Donec sit amet dui dolor. Nam tempor condimentum efficitur. " +
                "Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere " +
                "cubilia curae; Fusce finibus elit et enim vulputate, et accumsan lacus aliquet. " +
                "Nunc varius efficitur condimentum. Duis lobortis lacus elit, ut eleifend " +
                "ligula molestie ac.",
        )
    }
}
