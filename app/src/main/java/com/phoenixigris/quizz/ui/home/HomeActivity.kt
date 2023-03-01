package com.phoenixigris.quizz.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.phoenixigris.quizz.databinding.HomeLytBinding
import com.phoenixigris.quizz.helpers.Constants.QUIZ_TYPE
import com.phoenixigris.quizz.helpers.QuizLevelEnum
import com.phoenixigris.quizz.helpers.QuizModel
import com.phoenixigris.quizz.helpers.QuizStatus
import com.phoenixigris.quizz.helpers.QuizTypeEnum
import com.phoenixigris.quizz.ui.profile.ProfileActivity
import com.phoenixigris.quizz.ui.quiz.QuizActivity
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "HomeActivity"

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: HomeLytBinding
    private val viewModel: HomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeLytBinding.inflate(layoutInflater)
        viewModel.getQuizStatus()
        observerQuizStatus()
        setProfile()
        //  fetchQuestions()
        setContentView(binding.root)
    }

    private fun setProfile() {
        binding.homeActProfileIV.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun observerQuizStatus() {
        viewModel.quizStatusList.observe(this) {
            binding.homeLytRecycleV.apply {
                layoutManager = LinearLayoutManager(this@HomeActivity)
                adapter = QuizCategoryRVA(it) { quizModel, quizLevelEnum ->
                    onPlayClicked(quizModel, quizLevelEnum)
                }
            }
        }
    }

    private fun onPlayClicked(data: QuizModel, quizLevelEnum: QuizLevelEnum) {
        when (quizLevelEnum) {
            QuizLevelEnum.EASY -> {
                startQuiz(data)
            }
            QuizLevelEnum.MEDIUM -> {
                if (data.status.easy != QuizStatus.COMPLETED) {
                    Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            QuizLevelEnum.HARD -> {
                if (data.status.easy != QuizStatus.COMPLETED && data.status.medium != QuizStatus.COMPLETED) {
                    Toast.makeText(
                        this,
                        "Coming Soon",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun startQuiz(data: QuizModel) {
        val intent = Intent(this, QuizActivity::class.java)
        when (data.quizType) {
            QuizTypeEnum.LINUX -> {
                intent.putExtra(QUIZ_TYPE, QuizTypeEnum.LINUX)
            }
            QuizTypeEnum.DEVOPS -> {
                intent.putExtra(QUIZ_TYPE, QuizTypeEnum.DEVOPS)
            }
            QuizTypeEnum.DOCKER -> {
                intent.putExtra(QUIZ_TYPE, QuizTypeEnum.DOCKER)
            }
            QuizTypeEnum.SQL -> {
                intent.putExtra(QUIZ_TYPE, QuizTypeEnum.SQL)
            }
            QuizTypeEnum.CODE -> {
                intent.putExtra(QUIZ_TYPE, QuizTypeEnum.CODE)
            }
            QuizTypeEnum.CMS -> {
                intent.putExtra(QUIZ_TYPE, QuizTypeEnum.CMS)
            }
        }
        startActivity(intent)
    }

    private fun fetchQuestions() {
        viewModel.fetchQuestions()
    }


}