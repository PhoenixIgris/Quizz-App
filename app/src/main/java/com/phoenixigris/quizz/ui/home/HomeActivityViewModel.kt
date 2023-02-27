package com.phoenixigris.quizz.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeActivityViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {

    val randomQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()
    val linuxQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()
    val devOpsQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()
    val dockerQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()
    val sqlQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()
    val codeQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()
    val cmsQuestionList = dataStoreHelper.getRandomQuestion().asLiveData()

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