package com.example.questionnaire.domain

import android.util.Log
import com.example.questionnaire.data.models.QuestionTree
import com.example.questionnaire.data.treeData

object Questionnaire {

    var currentQuestion = treeData()

    var previousQuestion: QuestionTree? = null

    fun goToNextQuestion(
        questionTree: QuestionTree
    ) {
        Log.d("TAG", "goToNextQuestion ${questionTree.questionText}")
        previousQuestion = currentQuestion.copy()
        currentQuestion = questionTree
    }

    fun goToPreviousQuestion() {
        currentQuestion.previousQuestion?.let {
            currentQuestion = it
        }
    }

    fun isPreviousQuestionExists(): Boolean {
        return currentQuestion.previousQuestion != null
    }
}