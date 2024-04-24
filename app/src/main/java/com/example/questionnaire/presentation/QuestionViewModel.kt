package com.example.questionnaire.presentation

import androidx.lifecycle.ViewModel
import com.example.questionnaire.data.models.QuestionTree
import com.example.questionnaire.domain.Questionnaire
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuestionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState: StateFlow<QuestionUiState> = _uiState.asStateFlow()

    fun goToNextQuestion(questionTree: QuestionTree) {
        Questionnaire.goToNextQuestion(questionTree)

        _uiState.update { currentState ->
            currentState.copy(
                currentQuestion = Questionnaire.currentQuestion,
                isPreviousQuestionExists = true
            )
        }
    }

    fun goToPreviousQuestion() {
        if (Questionnaire.isPreviousQuestionExists()) {
            Questionnaire.goToPreviousQuestion()

            _uiState.update { currentState ->
                currentState.copy(
                    currentQuestion = Questionnaire.currentQuestion,
                    isPreviousQuestionExists = true
                )
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isPreviousQuestionExists = false
                )
            }
        }
    }
}
