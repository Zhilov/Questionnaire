package com.example.questionnaire.presentation

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.util.Log
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    fun reloadTree(jsonText: String)
    {
        Log.d("reloadTreejsonText", jsonText ?: "Empty")

        Questionnaire.reloadTree(jsonText);

        _uiState.update { currentState ->
            currentState.copy(
                currentQuestion = Questionnaire.currentQuestion,
                isPreviousQuestionExists = false
            )
        }
    }
}

