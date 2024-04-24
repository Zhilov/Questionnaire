package com.example.questionnaire.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import java.lang.reflect.TypeVariable

@Composable
fun AnimatedText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.displaySmall
) {
    var oldText by remember {
        mutableStateOf(text)
    }
    SideEffect {
        oldText = text
    }
    Row(modifier = modifier) {
        val oldCountString = oldText
        for (i in text.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = text[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                text[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }
            ) { char ->
                Text(
                    text = char.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}
