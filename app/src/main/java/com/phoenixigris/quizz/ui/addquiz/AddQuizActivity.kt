package com.phoenixigris.quizz.ui.addquiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.ui.others.ComingSoonFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddQuizActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_quiz)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ComingSoonFragment())
                .commit()
        }
    }
}