package com.example.questionnaire.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.questionnaire.data.models.QuestionTree
import com.example.questionnaire.domain.Questionnaire
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuestionUiState())
    val uiState: StateFlow<QuestionUiState> = _uiState.asStateFlow()

    suspend fun goToNextQuestion(questionTree: QuestionTree) {
        Questionnaire.goToNextQuestion(questionTree)

        coroutineScope {
            this.launch(Dispatchers.IO) {
                Questionnaire.stream.collect {
                    _uiState.update { currentState ->
                        currentState.copy(
                            currentQuestion = it!!,
                            isPreviousQuestionExists = true
                        )
                    }
                }
            }
        }
    }

    suspend fun goToPreviousQuestion() {
        if (Questionnaire.isPreviousQuestionExists()) {
            Questionnaire.goToPreviousQuestion()

            coroutineScope {
                this.launch(Dispatchers.IO) {
                    Questionnaire.stream.collect {
                        _uiState.update { currentState ->
                            currentState.copy(
                                currentQuestion = it!!,
                                isPreviousQuestionExists = true
                            )
                        }
                    }
                }
            }
        } else {
            _uiState.update { currentState ->
                currentState.copy(
                    isPreviousQuestionExists = false
                )
            }
        }
    }

    fun reloadTree(jsonText: String) {
        Log.d("reloadTreejsonText", jsonText)

        Questionnaire.reloadTree(jsonText)

        _uiState.update { currentState ->
            currentState.copy(
                currentQuestion = Questionnaire.value,
                isPreviousQuestionExists = false
            )
        }
    }
}

