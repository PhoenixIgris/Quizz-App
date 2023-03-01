package com.phoenixigris.quizz.helpers

data class QuizModel(
    val name: String,
    val status: Level,
    val quizType: QuizTypeEnum,
    val drawableId: Int,
    val score: Score
)

data class Level(
    val easy: QuizStatus,
    val medium: QuizStatus,
    val hard: QuizStatus
)

data class Score(
    val easyScore: Int,
    val mediumScore: Int,
    val hardScore: Int
)
