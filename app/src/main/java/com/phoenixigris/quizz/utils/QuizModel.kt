package com.phoenixigris.quizz.utils

data class QuizModel(
    val name: String,
    val level: QuizLevelEnum,
    val category: com.phoenixigris.quizz.ui.home.model.QuizCategoryModel,
    val score: Int
)
