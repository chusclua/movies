package com.chus.clua.presentation.composable

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = LocalTextStyle.current
) {

    if (text.isEmpty()) return

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        var showMore by remember { mutableStateOf(false) }

        Column(
            modifier = modifier
                .animateContentSize(animationSpec = tween(100))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { showMore = !showMore }
        ) {

            if (showMore) {
                Text(text = text, style = style)
            } else {
                Text(
                    text = text,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = style
                )
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowDown,
                    contentDescription = "",
                    modifier = Modifier.size(16.dp).align(Alignment.End)
                )
            }
        }
    }
}