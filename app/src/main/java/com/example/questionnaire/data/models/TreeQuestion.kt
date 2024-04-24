package com.example.questionnaire.data.models

data class QuestionTree(
    val questionText: String,
    val questionAnswers: MutableList<QuestionAnswer>,
    var previousQuestion: QuestionTree? = null
)

data class QuestionAnswer(
    val answerText: String,
    val questionTree: QuestionTree
)