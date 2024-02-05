package com.phoenixigris.quizz.ui.quiz

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixigris.quizz.repository.QuizRepository
import com.phoenixigris.quizz.ui.home.model.QuizCategoryModel
import com.phoenixigris.quizz.utils.QuizLevelEnum
import com.phoenixigris.quizz.utils.QuizModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizActivityViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {
    fun storeResult(category: QuizCategoryModel?, level: String, score: Int) {
        viewModelScope.launch {
            val newList = mutableListOf<QuizModel>()
            category?.let {
                val list = quizRepository.getQuizStatusList().first()
                    .filter {
                        category.id != it.category.id
                    }
                newList.addAll(list)
                newList.add(
                    QuizModel(
                        name = category.name,
                        level = QuizLevelEnum.valueOf(level),
                        score = score,
                        category = category
                    )
                )
            }
            Log.e("Debug", "storeResult: 7 $newList ")
            quizRepository.setQuizStatusList(newList)
        }
    }
}