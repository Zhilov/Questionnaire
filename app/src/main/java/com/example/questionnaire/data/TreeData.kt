package com.example.questionnaire.data

import com.example.questionnaire.data.models.QuestionAnswer
import com.example.questionnaire.data.models.QuestionTree

fun treeData(): QuestionTree {
    val treeVertex = QuestionTree(
        questionText = "Вопрос 0",
        questionAnswers = mutableListOf()
    )

    val question1 = QuestionTree(
        questionText = "Вопрос 1",
        mutableListOf()
    )

    val question2 = QuestionTree(
        questionText = "Вопрос 2",
        mutableListOf()
    )

    val question3 = QuestionTree(
        questionText = "Вопрос 3",
        mutableListOf()
    )

    val question11 = QuestionTree(
        questionText = "Вопрос 1.1",
        mutableListOf()
    )

    val question12 = QuestionTree(
        questionText = "Вопрос 1.2",
        mutableListOf()
    )

    val question13 = QuestionTree(
        questionText = "Вопрос 1.3",
        mutableListOf()
    )

    val question111 = QuestionTree(
        questionText = "Привет всем меня зовут ивангай я лучший ютубер в украине и россии но идите нахуй сегодня я не выспался Привет всем меня зовут ивангай я лучший ютубер в украине и россии но идите нахуй сегодня я не выспался Привет всем меня зовут ивангай я лучший ютубер в украине и россии но идите нахуй сегодня я не выспался Привет всем меня зовут ивангай я лучший ютубер в украине и россии но идите нахуй сегодня я не выспался Привет всем меня зовут ивангай я лучший ютубер в украине и россии но идите нахуй сегодня я не выспался Привет всем меня зовут ивангай я лучший ютубер в украине и россии но идите нахуй сегодня я не выспался",
        mutableListOf()
    )

    val question112 = QuestionTree(
        questionText = "Вопрос 1.1.2",
        mutableListOf()
    )

    val question113 = QuestionTree(
        questionText = "Вопрос 1.1.3",
        mutableListOf()
    )

    treeVertex.questionAnswers.add(QuestionAnswer("Ответ 1", question1))
    question1.previousQuestion = treeVertex
    treeVertex.questionAnswers.add(QuestionAnswer("Ответ 2", question2))
    question2.previousQuestion = treeVertex
    treeVertex.questionAnswers.add(QuestionAnswer("Ответ 3", question3))
    question3.previousQuestion = treeVertex

    question1.questionAnswers.add(QuestionAnswer("Ответ 1.1", question11))
    question11.previousQuestion = question1
    question1.questionAnswers.add(QuestionAnswer("Ответ 1.2", question12))
    question12.previousQuestion = question1
    question1.questionAnswers.add(QuestionAnswer("Ответ 1.3", question13))
    question13.previousQuestion = question1


    question11.questionAnswers.add(QuestionAnswer("Ответ 1.1.1", question111))
    question111.previousQuestion = question11
    question11.questionAnswers.add(QuestionAnswer("Ответ 1.1.2", question112))
    question112.previousQuestion = question11
    question11.questionAnswers.add(QuestionAnswer("Ответ 1.1.3", question113))
    question113.previousQuestion = question11

    return treeVertex
}

/*
void AddPreviousQuestion(QuestionTree previousQuestion, QuestionTree questionTree)
{
    questionTree.previousQuestion = previousQuestion;
    if (questionTree.questionAnswers.Count > 0)
    {
        foreach (QuestionAnswer answer in questionTree.questionAnswers)
        {
            if (answer.questionTree != null)
            {
                AddPreviousQuestion(questionTree, answer.questionTree);
            }
        }
    }
}
*/

fun addPreviousQuestion(questionTree: QuestionTree, previousQuestionTree: QuestionTree?){
    questionTree.previousQuestion = previousQuestionTree;
    if (questionTree.questionAnswers.count() > 0)
    {
        questionTree.questionAnswers.forEach{ answer ->
            if (answer.questionTree != null)
            {
                addPreviousQuestion(questionTree, answer.questionTree!!);
            }
        }
    }
}