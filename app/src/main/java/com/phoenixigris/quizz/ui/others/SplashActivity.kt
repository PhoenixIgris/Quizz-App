package com.phoenixigris.quizz.ui.others

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.phoenixigris.quizz.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}