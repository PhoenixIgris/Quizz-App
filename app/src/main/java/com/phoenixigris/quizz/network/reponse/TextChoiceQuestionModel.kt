package com.phoenixigris.quizz.network.reponse

data class TextChoiceQuestionModel(
    val category: String,
    val correctAnswer: String,
    val difficulty: String,
    val id: String,
    val incorrectAnswers: List<String>,
    val isNiche: Boolean,
    val question: Question,
    val regions: List<String>,
    val tags: List<String>,
    val type: String
)

data class Question(
    val text: String
)