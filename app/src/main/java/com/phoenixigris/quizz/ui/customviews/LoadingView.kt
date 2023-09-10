package com.phoenixigris.quizz.ui.customviews

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.LinearInterpolator
import androidx.appcompat.widget.AppCompatImageView

class LoadingView(context: Context, attrs: AttributeSet? = null) :
    AppCompatImageView(context, attrs) {

    init {
        val rotationAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)
        rotationAnimator.duration = 2000
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE
        rotationAnimator.interpolator = LinearInterpolator()
        rotationAnimator.start()
    }
}