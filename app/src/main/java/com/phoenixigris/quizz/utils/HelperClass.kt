package com.phoenixigris.quizz.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import com.phoenixigris.quizz.R

object HelperClass {

    fun linearGradientDrawable(
        color1: String,
        color2: String,
        color3: String,
        orientation: GradientDrawable.Orientation,
        radius: Float = 0F
    ): GradientDrawable {
        return GradientDrawable().apply {
            colors = intArrayOf(
                Color.parseColor(color1),
                Color.parseColor(color2),
                Color.parseColor(color3)
            )
            gradientType = GradientDrawable.LINEAR_GRADIENT
            this.orientation = orientation
            cornerRadius = radius
        }
    }

    fun getQuizCategoryList(): List<QuizModel> {
        val list = mutableListOf<QuizModel>()
        val initialScore = Score(easyScore = 0, mediumScore = 0, hardScore = 0)
        val initialStatus = Level(
            easy = QuizStatus.INCOMPLETE,
            medium = QuizStatus.INCOMPLETE,
            hard = QuizStatus.INCOMPLETE,
        )
        list.add(
            QuizModel(
                name = Constants.LINUX,
                status = initialStatus,
                quizType = QuizTypeEnum.LINUX,
                score = initialScore,
                drawableId = R.drawable.random
            )
        )
        list.add(
            QuizModel(
                name = Constants.DEVOPS,
                status = initialStatus,
                quizType = QuizTypeEnum.DEVOPS,
                score = initialScore,
                drawableId = R.drawable.random
            )
        )
        list.add(
            QuizModel(
                name = Constants.DOCKER,
                status = initialStatus,
                quizType = QuizTypeEnum.DOCKER,
                score = initialScore,
                drawableId = R.drawable.random
            )
        )
        list.add(
            QuizModel(
                name = Constants.SQL,
                status = initialStatus,
                quizType = QuizTypeEnum.SQL,
                score = initialScore,
                drawableId = R.drawable.random
            )
        )
        list.add(
            QuizModel(
                name = Constants.CODE,
                status = initialStatus,
                quizType = QuizTypeEnum.CODE,
                score = initialScore,
                drawableId = R.drawable.random
            )
        )
        list.add(
            QuizModel(
                name = Constants.CMS,
                status = initialStatus,
                quizType = QuizTypeEnum.CMS,
                score = initialScore,
                drawableId = R.drawable.random
            )
        )
        return list
    }
}