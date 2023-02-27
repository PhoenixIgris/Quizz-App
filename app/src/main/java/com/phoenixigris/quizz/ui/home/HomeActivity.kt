package com.phoenixigris.quizz.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.phoenixigris.quizz.databinding.HomeLytBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeActivity"

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeLytBinding
    private val viewModel: HomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeLytBinding.inflate(layoutInflater)
        fetchQuestions()
        setContentView(binding.root)
    }

    private fun fetchQuestions() {
        viewModel.fetchQuestions()
    }
}