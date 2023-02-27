package com.phoenixigris.quizz.network.reponse

data class QuestionResponseItem(
    val answers: Answers,
    val category: String,
    val correct_answer: String,
    val correct_answers: CorrectAnswers,
    val description: Any,
    val difficulty: String,
    val explanation: Any,
    val id: Int,
    val multiple_correct_answers: String,
    val question: String,
    val tags: List<Tag>,
    val tip: Any
)