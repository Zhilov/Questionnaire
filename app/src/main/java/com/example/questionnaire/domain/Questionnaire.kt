package com.example.questionnaire.domain

import android.util.Log
import com.example.questionnaire.data.models.QuestionTree
import com.example.questionnaire.data.treeData
import com.example.questionnaire.data.parseJson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

object Questionnaire {

    private val currentQuestionFlow = MutableStateFlow(treeData())

    var value: QuestionTree
        get() = currentQuestionFlow.value
        set(value) {
            currentQuestionFlow.value = value
        }

    val stream: Flow<QuestionTree?> = currentQuestionFlow

    var previousQuestion: QuestionTree? = null

    fun goToNextQuestion(
        questionTree: QuestionTree
    ) {
        Log.d("TAG", "goToNextQuestion ${questionTree.questionText}")
        previousQuestion = currentQuestionFlow.value.copy()
        currentQuestionFlow.value = questionTree
    }

    fun goToPreviousQuestion() {
        currentQuestionFlow.value.previousQuestion?.let {
            currentQuestionFlow.value = it
        }
    }

    fun isPreviousQuestionExists(): Boolean {
        return currentQuestionFlow.value.previousQuestion != null
    }

    fun reloadTree(jsonText: String)
    {
        Log.d("reloadTree", "Start")
        val newTree: QuestionTree = parseJson(jsonText)

        //addPreviousQuestion(newTree, null)
        Log.d("reloadTree", newTree.questionText)
        currentQuestionFlow.value = newTree
        previousQuestion = newTree.previousQuestion
    }
}