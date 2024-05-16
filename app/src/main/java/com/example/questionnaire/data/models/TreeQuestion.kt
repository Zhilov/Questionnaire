package com.example.questionnaire.data.models

import kotlinx.serialization.Serializable

@Serializable
data class QuestionTree(
    var questionText: String = "",
    var questionAnswers: MutableList<QuestionAnswer>,
    var previousQuestion: QuestionTree? = null
)

@Serializable
data class QuestionAnswer(
    var answerText: String,
    var questionTree: QuestionTree? = null
)