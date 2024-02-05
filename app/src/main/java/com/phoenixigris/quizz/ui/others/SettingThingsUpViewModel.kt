package com.phoenixigris.quizz.ui.others

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phoenixigris.quizz.database.DataStoreHelper
import com.phoenixigris.quizz.repository.QuizRepository
import com.phoenixigris.quizz.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SettingThingsUpViewModel @Inject constructor(
    private val quizRepository: QuizRepository,
    private val dataStoreHelper: DataStoreHelper
) : ViewModel() {
    private val _fetchCompleteLiveData = MutableLiveData<Boolean>()
    val fetchCompleteLiveData: LiveData<Boolean> get() = _fetchCompleteLiveData
    var isUserLoggedIn = false

    private val _progressLiveData = MutableLiveData(0)
    val progressLiveData: LiveData<Int> get() = _progressLiveData

    private val operationQueue = getRequiredOperationQueue()
    private var anyOperationFailed = false


    fun fetchEverythingNeeded() {
        viewModelScope.launch {
            while (!operationQueue.isEmpty()) {
                operationQueue.remove().invoke()
                if (anyOperationFailed) {
                    break
                }
            }
            if (!anyOperationFailed) {
                _fetchCompleteLiveData.value = true
            }
        }
    }

    private fun getRequiredOperationQueue(): Queue<suspend () -> Unit> {
        return LinkedList<suspend () -> Unit>().apply {
            add { fetchUserDetails() }
            add { quizRepository.getCategories() }

//            add { quizRepository.fetchRandomQuestions() }
//            add { quizRepository.fetchLinuxQuestions() }
//            add { quizRepository.fetchDevOpsQuestions() }
//            add { quizRepository.fetchDockerQuestions() }
//            add { quizRepository.fetchSqlQuestions() }
//            add { quizRepository.fetchCodeQuestions() }
//            add { quizRepository.fetchCMSQuestions() }

        }
    }

    private suspend fun fetchUserDetails() {
        isUserLoggedIn = dataStoreHelper.readBooleanFromDatastore(Constants.IS_USER_LOGGED_IN)
    }

}