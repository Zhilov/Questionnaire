package com.example.questionnaire.presentation

import com.example.questionnaire.data.models.QuestionTree
import com.example.questionnaire.data.treeData

/**
 * Data class that represents the game UI state
 */
data class QuestionUiState(
    val currentQuestion: QuestionTree = treeData(),
    val isPreviousQuestionExists: Boolean = false
    )
