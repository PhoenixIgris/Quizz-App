package com.phoenixigris.quizz.network.reponse

data class QuizCategoryModel(
    val trivia_categories: List<TriviaCategory>
)

data class TriviaCategory(
    val id: Int,
    val name: String
)