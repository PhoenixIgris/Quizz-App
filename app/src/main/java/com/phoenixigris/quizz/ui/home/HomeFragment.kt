package com.phoenixigris.quizz.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.slider.Slider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.phoenixigris.quizz.R
import com.phoenixigris.quizz.databinding.HomeLytBinding
import com.phoenixigris.quizz.network.safeapicall.ApiCallListener
import com.phoenixigris.quizz.ui.home.model.QuizCategoryModel
import com.phoenixigris.quizz.ui.profile.ProfileFragment
import com.phoenixigris.quizz.ui.quiz.QuizActivity
import com.phoenixigris.quizz.utils.GridSpacingItemDecoration
import com.phoenixigris.quizz.utils.IntentParams
import com.phoenixigris.quizz.utils.QuizLevelEnum
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "HomeFragment"

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: HomeLytBinding
    private val viewModel: HomeFragmentViewModel by viewModels()
    private var selectModeDialog: AlertDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeLytBinding.inflate(layoutInflater)
        viewModel.getQuizStatus()
        binding.progress.setAnimation(R.raw.loading)
        observerQuizStatus()
        setProfile()
        return binding.root
    }

    private fun setProfile() {
        binding.homeActProfileIV.setOnClickListener {
            findNavController().navigate(R.id.profileFragment)
        }
    }

    private fun observerQuizStatus() {
        viewModel.quizStatusList.observe(viewLifecycleOwner) {
            binding.homeLytRecycleV.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                addItemDecoration(
                    GridSpacingItemDecoration(
                        2,
                        30,
                        includeEdge = false, 0
                    )
                )
                adapter = QuizCategoryRVA(it) { quizModel, _ ->
                    onPlayClicked(quizModel)
                }
            }
        }
    }

    private fun onPlayClicked(data: QuizCategoryModel) {
        startQuiz(data)
    }

    private fun startQuiz(data: QuizCategoryModel) {
        var selectedQuizLevel = QuizLevelEnum.EASY
        selectModeDialog =
            AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme).apply {
                setCancelable(true)
                setView(
                    LayoutInflater.from(context).inflate(R.layout.fragment_mode_select_dialog, null)
                        .apply {
                            val easyCard = findViewById<ImageView>(R.id.easy)
                            val mediumCard = findViewById<ImageView>(R.id.medium)
                            val hardCard = findViewById<ImageView>(R.id.hard)
                            val label = findViewById<TextView>(R.id.levelValue)
                            findViewById<TextView>(R.id.title).text = data.name
                            findViewById<Slider>(R.id.slider)?.addOnChangeListener { _, value, _ ->
                                when {
                                    value <= 33 -> {
                                        selectedQuizLevel = QuizLevelEnum.EASY
                                        label.text =
                                            QuizLevelEnum.EASY.toString()
                                        easyCard.isVisible = true
                                        mediumCard.isVisible = false
                                        hardCard.isVisible = false
                                    }

                                    value <= 66 -> {
                                        selectedQuizLevel = QuizLevelEnum.HARD
                                        label.text =
                                            QuizLevelEnum.MEDIUM.toString()
                                        easyCard.isVisible = true
                                        mediumCard.isVisible = true
                                        hardCard.isVisible = false
                                    }

                                    else -> {
                                        selectedQuizLevel = QuizLevelEnum.HARD
                                        label.text =
                                            QuizLevelEnum.HARD.toString()
                                        easyCard.isVisible = true
                                        mediumCard.isVisible = true
                                        hardCard.isVisible = true
                                    }
                                }
                            }
                            findViewById<Button>(R.id.play_btn).setOnClickListener {
                                fetchQuizQuestions(data, selectedQuizLevel)
                                selectModeDialog?.dismiss()
                            }
                        })
            }.create()
        selectModeDialog?.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setGravity(Gravity.CENTER)
        }

        selectModeDialog?.show()


    }

    private fun fetchQuizQuestions(category: QuizCategoryModel, selectedQuizLevel: QuizLevelEnum) {
        binding.loading.isVisible = true
        viewModel.getTriviaQuestionsList(
            category.id,
            selectedQuizLevel.name.lowercase(),
            object : ApiCallListener {
                override fun onError(errorMessage: String?) {
                    binding.loading.isVisible = false
                    Snackbar.make(
                        binding.root,
                        errorMessage ?: "Error Occurred while fetching questions",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                override fun onSuccess(message: String?, data: String?) {
                    binding.loading.isVisible = false
                    val i = Intent(requireContext(), QuizActivity::class.java)
                    i.apply {
                        putExtra(IntentParams.TRIVIA_QUESTIONS, data)
                        putExtra(IntentParams.SELECTED_CATEGORY, Gson().toJson(category))
                        putExtra(IntentParams.SELECTED_LEVEL, selectedQuizLevel.name)
                    }
                    startActivity(i)
                }

            })
    }


}