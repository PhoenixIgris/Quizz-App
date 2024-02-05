package com.phoenixigris.quizz.network.reponse

data class QuestionAnswerListResponse(
    val response_code: Int,
    val results: List<Result>? = emptyList()
)


data class Result(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)