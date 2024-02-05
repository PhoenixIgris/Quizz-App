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
}