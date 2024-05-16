package com.example.questionnaire.data
import android.util.Log
import com.example.questionnaire.data.models.QuestionTree
import kotlinx.serialization.json.Json

fun parseJson(jsonString: String): QuestionTree{
    var result: QuestionTree = Json.decodeFromString<QuestionTree>(jsonString);
    Log.d("parseJson", result.questionText);
    return result
}
