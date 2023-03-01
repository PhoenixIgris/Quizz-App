package com.phoenixigris.quizz.ui.home

import android.util.Log
import androidx.lifecycle.*
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.helpers.QuizModel
import com.phoenixigris.quizz.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "HomeActivityViewModel"
@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
) : ViewModel() {
    private val _quizStatusList =
        MutableLiveData<List<QuizModel>>()
    val quizStatusList: LiveData<List<QuizModel>> get() = _quizStatusList

    val randomQuestionList = quizRepository.getRandomQuestionList().asLiveData()
    val linuxQuestionList = quizRepository.getLinuxQuestion().asLiveData()
    val devOpsQuestionList = quizRepository.getDevOpsQuestionList().asLiveData()
    val dockerQuestionList = quizRepository.getDockerQuestionList().asLiveData()
    val sqlQuestionList = quizRepository.getSqlQuestionList().asLiveData()
    val codeQuestionList = quizRepository.getCodeQuestionList().asLiveData()
    val cmsQuestionList = quizRepository.getCmsQuestionList().asLiveData()

    fun getQuizStatus() {
        viewModelScope.launch {
            Log.e(TAG, "getQuizStatus: ${quizRepository.getQuizStatusList().first()}", )
            _quizStatusList.value = quizRepository.getQuizStatusList().first()
        }
    }

    fun fetchQuestions() {
        viewModelScope.launch {
            quizRepository.apply {
                fetchRandomQuestions()
                fetchLinuxQuestions()
                fetchDevOpsQuestions()
                fetchDockerQuestions()
                fetchSqlQuestions()
                fetchCodeQuestions()
                fetchCMSQuestions()
            }
        }
    }
}