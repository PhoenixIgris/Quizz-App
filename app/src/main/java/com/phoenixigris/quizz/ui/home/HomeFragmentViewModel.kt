package com.phoenixigris.quizz.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.phoenixigris.quizz.network.safeapicall.ApiCallListener
import com.phoenixigris.quizz.network.safeapicall.Resource
import com.phoenixigris.quizz.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private const val TAG = "HomeActivityViewModel"

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val _quizStatusList =
        MutableLiveData<List<com.phoenixigris.quizz.ui.home.model.QuizCategoryModel>>()
    val quizStatusList: LiveData<List<com.phoenixigris.quizz.ui.home.model.QuizCategoryModel>> get() = _quizStatusList

//    val randomQuestionList = quizRepository.getRandomQuestionList().asLiveData()
//    val linuxQuestionList = quizRepository.getLinuxQuestion().asLiveData()
//    val devOpsQuestionList = quizRepository.getDevOpsQuestionList().asLiveData()
//    val dockerQuestionList = quizRepository.getDockerQuestionList().asLiveData()
//    val sqlQuestionList = quizRepository.getSqlQuestionList().asLiveData()
//    val codeQuestionList = quizRepository.getCodeQuestionList().asLiveData()
//    val cmsQuestionList = quizRepository.getCmsQuestionList().asLiveData()

    fun getQuizStatus() {
        viewModelScope.launch {
            Log.e(TAG, "getQuizStatus: ${quizRepository.getQuizStatusList().first()}")
            _quizStatusList.value = quizRepository.getCategoryList()
        }
    }


    fun getTriviaQuestionsList(categoryId: Int?, difficulty: String?, callback: ApiCallListener) {
        viewModelScope.launch {

//delay(5000)
//            runBlocking {
//
//                callback.onSuccess(
//                    "dummy data", """{
//                "response_code": 0,
//                "results": [
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "In the video-game franchise Kingdom Hearts, the main protagonist, carries a weapon with what shape?",
//                    "correct_answer": "Key",
//                    "incorrect_answers": [
//                    "Sword",
//                    "Pen",
//                    "Cellphone"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "What does the &#039;S&#039; stand for in the abbreviation SIM, as in SIM card? ",
//                    "correct_answer": "Subscriber",
//                    "incorrect_answers": [
//                    "Single",
//                    "Secure",
//                    "Solid"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "What geometric shape is generally used for stop signs?",
//                    "correct_answer": "Octagon",
//                    "incorrect_answers": [
//                    "Hexagon",
//                    "Circle",
//                    "Triangle"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "Which of the following card games revolves around numbers and basic math?",
//                    "correct_answer": "Uno",
//                    "incorrect_answers": [
//                    "Go Fish",
//                    "Twister",
//                    "Munchkin"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "Which sign of the zodiac comes between Virgo and Scorpio?",
//                    "correct_answer": "Libra",
//                    "incorrect_answers": [
//                    "Gemini",
//                    "Taurus",
//                    "Capricorn"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "What is the Zodiac symbol for Gemini?",
//                    "correct_answer": "Twins",
//                    "incorrect_answers": [
//                    "Fish",
//                    "Scales",
//                    "Maiden"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "What is Cynophobia the fear of?",
//                    "correct_answer": "Dogs",
//                    "incorrect_answers": [
//                    "Birds",
//                    "Flying",
//                    "Germs"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "When someone is inexperienced they are said to be what color?",
//                    "correct_answer": "Green",
//                    "incorrect_answers": [
//                    "Red",
//                    "Blue",
//                    "Yellow"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "Who is the author of Jurrasic Park?",
//                    "correct_answer": "Michael Crichton",
//                    "incorrect_answers": [
//                    "Peter Benchley",
//                    "Chuck Paluhniuk",
//                    "Irvine Welsh"
//                    ]
//                },
//                {
//                    "category": "General Knowledge",
//                    "type": "multiple",
//                    "difficulty": "easy",
//                    "question": "What is the official language of Brazil?",
//                    "correct_answer": "Portugese",
//                    "incorrect_answers": [
//                    "Brazilian",
//                    "Spanish",
//                    "English"
//                    ]
//                }
//                ]
//            }
//"""
//                )
//            }
            when (val response = quizRepository.getTriviaQuestionsList(categoryId, difficulty)) {
                is Resource.Failure -> {
                    callback.onError(response.errorMsg)
                }

                Resource.Loading -> {}
                is Resource.Success -> {
                    callback.onSuccess(
                        response.value.response_code.toString(),
                        Gson().toJson(response.value)
                    )
                }
            }
        }
    }


}