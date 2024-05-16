package com.example.questionnaire.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.questionnaire.R
import com.example.questionnaire.data.models.QuestionAnswer
import com.example.questionnaire.data.models.QuestionTree
import kotlinx.coroutines.launch

@Composable
fun Question(
    importFileClick: () -> Unit,
    questionViewModel: QuestionViewModel = viewModel()
) {
    val questionUiState by questionViewModel.uiState.collectAsState()

    val currentQuestion = questionUiState.currentQuestion

    InitialFadeIn {
        Column {

            val scope = rememberCoroutineScope()

            AppBarTitle(
                modifier = Modifier.padding(12.dp),
                onBackClick = {
                    scope.launch {
                        questionViewModel.goToPreviousQuestion()
                    }
                },
                onUploadFileClick = importFileClick,
                isPreviousQuestionExists = questionUiState.isPreviousQuestionExists
            )
            ScreenBody(
                currentQuestion = currentQuestion,
                onClick = {
                    scope.launch {
                        questionViewModel.goToNextQuestion(it.questionTree!!)
                    }
                }
            )
        }
    }
}

@Composable
fun AppBarTitle(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onUploadFileClick: () -> Unit,
    isPreviousQuestionExists: Boolean
) {
    AnimatedVisibility(visible = isPreviousQuestionExists) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = "",
                )
            }

            IconButton(
                onClick = onUploadFileClick
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_file_upload_24),
                    tint = MaterialTheme.colorScheme.surfaceTint,
                    contentDescription = "",
                )
            }
        }
    }
}

@Composable
fun ScreenBody(
    currentQuestion: QuestionTree,
    onClick: (QuestionAnswer) -> Unit
) {
    Column(
        verticalArrangement = if (currentQuestion.questionAnswers.isNotEmpty()) Arrangement.SpaceEvenly else Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        QuestionText(
            question = currentQuestion.questionText,
            answerModels = currentQuestion.questionAnswers
        )

        QuestionButtons(
            answerModels = currentQuestion.questionAnswers,
            onClick = onClick
        )
    }
}

@Composable
fun QuestionText(
    modifier: Modifier = Modifier,
    question: String,
    answerModels: List<QuestionAnswer>
) {
    AnimatedVisibility(visible = answerModels.isNotEmpty()) {
        AnimatedContent(
            targetState = question,
            transitionSpec = {
                addAnimation().using(
                    SizeTransform(clip = false)
                )
            }, label = ""
        ) { targetText ->
            AnimatedText(
                modifier = modifier.padding(12.dp),
                text = targetText
            )
        }
    }

    AnimatedVisibility(visible = answerModels.isEmpty()) {
        Text(
            modifier = modifier.padding(12.dp),
            fontSize = 20.sp,
            text = question
        )
    }
}

@Composable
fun QuestionButtons(
    answerModels: List<QuestionAnswer>,
    onClick: (QuestionAnswer) -> Unit
) {
    AnimatedVisibility(visible = answerModels.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(1f)
                .padding(bottom = 20.dp),
        ) {
            answerModels.forEach { questionAnswer ->
                Button(
                    modifier = Modifier.padding(12.dp),
                    onClick = { onClick(questionAnswer) }
                ) {
                    AnimatedContent(
                        targetState = questionAnswer.answerText,
                        transitionSpec = {
                            addAnimation().using(
                                SizeTransform(clip = false)
                            )
                        }, label = ""
                    ) { targetText ->
                        Text(
                            modifier = Modifier.padding(12.dp),
                            text = targetText
                        )
                    }
                }
            }
        }
    }
}

fun addAnimation(duration: Int = 500): ContentTransform {
    return (slideInVertically(animationSpec = tween(durationMillis = duration)) { height -> height } + fadeIn(
        animationSpec = tween(durationMillis = duration)
    )).togetherWith(slideOutVertically(animationSpec = tween(durationMillis = duration)) { height -> -height } + fadeOut(
        animationSpec = tween(durationMillis = duration)
    ))
}

@Composable
fun InitialFadeIn(
    durationMs: Int = 500,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    var visibility by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit, block = { visibility = true })
    AnimatedVisibility(visible = visibility, enter = fadeIn(tween(durationMs)), content = content)
}