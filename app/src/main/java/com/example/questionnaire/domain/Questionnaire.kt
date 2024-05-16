package com.example.questionnaire.domain

import android.util.Log
import com.example.questionnaire.data.models.QuestionTree
import com.example.questionnaire.data.treeData
import com.example.questionnaire.data.parseJson
import com.example.questionnaire.data.addPreviousQuestion

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

    fun reloadTree(jsonText: String)
    {
        var newTree: QuestionTree;
        Log.d("reloadTree", "Start");
        newTree = parseJson(jsonText);

        //addPreviousQuestion(newTree, null);
        Log.d("reloadTree", newTree.questionText);
        currentQuestion = newTree;
        previousQuestion = newTree.previousQuestion;
    }
}