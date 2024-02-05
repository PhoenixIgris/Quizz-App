package com.phoenixigris.quizz.ui.score

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phoenixigris.quizz.databinding.ActivityScoreBinding

class ScoreActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}