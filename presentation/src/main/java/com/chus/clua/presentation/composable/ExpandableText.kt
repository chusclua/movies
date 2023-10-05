package com.chus.clua.presentation.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.chus.clua.presentation.R

private const val MINIMIZED_MAX_LINES = 3

@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current
) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(buildAnnotatedString { append(text) }) }

    val color = MaterialTheme.colorScheme.primary
    val showMore = stringResource(id = R.string.show_more)
    val showLess = stringResource(id = R.string.show_less)

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {

        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                val annotatedString = buildAnnotatedString {
                    append(text)
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = color)) {
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
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = color)) {
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
        maxLines = if (isExpanded) Int.MAX_VALUE else MINIMIZED_MAX_LINES,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .clickable(
                enabled = isClickable,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { isExpanded = !isExpanded }
            .animateContentSize(animationSpec = tween(100)),
        style = style
    )
}